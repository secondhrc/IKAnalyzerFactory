package org.wltea.analyzer.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class IKTokenizerFactory extends TokenizerFactory implements ResourceLoaderAware {

	private List<String> protectWords = new ArrayList<String>();
	private String wordsFile;

	public IKTokenizerFactory(Map<String, String> args) {
		super(args);
		
		wordsFile = get(args, "words", "protwords.txt");
		if (!args.isEmpty())
			throw new IllegalArgumentException("Unknown parameters: " + args);
	}

	@Override
	public Tokenizer create(AttributeFactory factory, Reader reader) {
		return new IKTokenizer(factory, reader, protectWords);
	}

	@Override
	public void inform(ResourceLoader loader) throws IOException {

		InputStream is = null;
		try {
			is = loader.openResource(wordsFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
			String line = null;
			while ((line = br.readLine()) != null) {
				protectWords.add(line);
			}
		} catch (IOException e) {
			throw e;
		}finally{
			is.close();
		}

	}
}
