package org.wltea.analyzer.solr;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.SegmentingTokenizerBase;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

public class IKTokenizer extends SegmentingTokenizerBase {
	private static final BreakIterator sentenceProto = BreakIterator.getSentenceInstance(Locale.ROOT);
	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	private IKSegmenter _IKImplement;

	public IKTokenizer(Reader reader,List<String> protectWords) {
		this(DEFAULT_TOKEN_ATTRIBUTE_FACTORY, reader, protectWords);
	}

	public IKTokenizer(AttributeFactory factory, Reader reader,List<String> protectWords) {
		super(factory, reader, (BreakIterator) sentenceProto.clone());
		offsetAtt = addAttribute(OffsetAttribute.class);
		termAtt = addAttribute(CharTermAttribute.class);
		
		 loadKeyword(protectWords);		 
	//	_IKImplement = new IKSegmenter(reader, CustomConfig.getInstance());
		_IKImplement = new IKSegmenter(reader, true);

	}

	private void loadKeyword(List<String> protectWords) {
		Dictionary dic=Dictionary.initial(DefaultConfig.getInstance());
		dic.addWords(protectWords);
	}

	@Override
	protected boolean incrementWord() {
		clearAttributes();
		try {
			Lexeme nextLexeme = _IKImplement.next();
			if (nextLexeme != null) {
				termAtt.append(nextLexeme.getLexemeText());
				termAtt.setLength(nextLexeme.getLength());
				offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected void setNextSentence(int sentenceStart, int sentenceEnd) {
		String sentence = new String(this.buffer, sentenceStart, sentenceEnd - sentenceStart);
		StringReader reader=new StringReader(sentence);
		_IKImplement.reset(reader);
		

	}

	@Override
	public void reset() throws IOException {
		  super.reset();
		  _IKImplement.reset(input);
	}
}
