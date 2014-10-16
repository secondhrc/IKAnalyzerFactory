IKAnalyzerFactory
=================

IKAnalyzer兼容solr-4.9.0



schema.xml中，加入如下fieldType节点，即可

<fieldType name="text_general" class="solr.TextField" positionIncrementGap="100">  
      <analyzer type="index">  
      	<tokenizer class="org.wltea.analyzer.solr.IKTokenizerFactory"   />     
      	   
      	<!--以下内容可选-->
        <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/> 
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" enablePositionIncrements="true" />  
        <filter class="solr.LowerCaseFilterFactory"/>   
        <filter class="solr.KeywordMarkerFilterFactory" protected="protwords.txt" />      
        <!--以下内容可选end-->           
      </analyzer>  
       
      <analyzer type="query">  
      	
        <tokenizer class="org.wltea.analyzer.solr.IKTokenizerFactory"/>    
        <!--以下内容可选-->
        <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>   
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" enablePositionIncrements="true" />  
        <filter class="solr.LowerCaseFilterFactory"/>         
        <filter class="solr.KeywordMarkerFilterFactory" protected="protwords.txt" />    
        <!--以下内容可选end-->           
      </analyzer>  
    </fieldType>  
