package org.wltea.analyzer.dic;

import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.cfg.Configuration;

public class CustomConfig implements Configuration {
	
	private static CustomConfig config;
	
	private CustomConfig(){
		
	}
	
	public static Configuration getInstance(){
		if(config==null){
			config=new CustomConfig();
		}
		return config;
	}

	@Override
	public List<String> getExtDictionarys() {
		List<String> list=new ArrayList<String>();
		list.add("org/wltea/analyzer/dic/keyword.dic");
		return list;
	}

	@Override
	public List<String> getExtStopWordDictionarys() {
		List<String> list=new ArrayList<String>();
	//	list.add("org/wltea/analyzer/dic/stop.dic");
		return list;
	}

	@Override
	public String getMainDictionary() {
		return "org/wltea/analyzer/dic/main2012.dic";
	}

	@Override
	public String getQuantifierDicionary() {
		 return "org/wltea/analyzer/dic/quantifier.dic";
	}

	@Override
	public void setUseSmart(boolean smart) {
	
	}

	@Override
	public boolean useSmart() {	
		return true;
	}

}
