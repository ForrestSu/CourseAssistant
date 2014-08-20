package com.hunau.library;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.hunau.coursehelper.HUNAU;

public class LibParseHtml {

     	public List<BookInfo> AanalysisHtml(InputStream in) throws IOException {
     		List<BookInfo> books=new ArrayList<BookInfo>();
     		BookInfo book;
     		Document doc = Jsoup.parse(in,"GBK",HUNAU.url_lib_borrow);
     		Elements data =doc.select(".zt5");//解析出class=zt5的标签
     		
     		int len=data.size();
     		 System.out.println(len);
      		for (int i=0;i<len;i+=13) {
      			book=new BookInfo(); 
      			String code=data.get(i+1).text();
      			book.setCode(code.substring(0,code.length()-1));
      			book.setName(data.get(i+2).text());
      			book.setAuthor(data.get(i+3).text());
      			book.setPosition(data.get(i+6).text());
      			book.setBortime(data.get(i+7).text());
      			book.setRettime(data.get(i+8).text());
      			book.setContime(data.get(i+9).text());
      			book.setTimes(data.get(i+10).text());
      			book.setLib(data.get(i+11).text());
      			book.setState(data.get(i+12).text());
      			books.add(book);
      		}
      		return books;
	    }
    
     	
		
}

