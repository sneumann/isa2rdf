package net.toxbank.isa.parser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;

import net.toxbank.isa.ColumnHeader;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Just tab-delimited files parser
 * @author nina
 *
 * @param <E>
 */

public abstract class TabsParser<E> implements Iterator<E>, Closeable {
	protected BufferedReader reader;
	protected int count = 0;
	protected ColumnHeader<Resource>[] header;
	protected String[] tabs;

	
	public TabsParser(Reader in) {
		reader = new BufferedReader(in);
		
	}
	
	@Override
	public void close() throws IOException {
		if (reader!=null) reader.close();
		
	}

	@Override
	public boolean hasNext() {
		try {
			readHeader();
			count++;
			String line = reader.readLine();
			tabs = line==null?null:line.split("\t");
			if (tabs!=null)
			for (int i=0;i< tabs.length;i++)
				if (tabs[i]!=null)
					tabs[i] = tabs[i].replace("\"","").trim();
			
			return line != null;
		} catch (Exception x) {
			PrintWriter writer = new PrintWriter(System.err);
			x.printStackTrace(writer);
			writer.flush();
			writer.close();
			
			return false;
		}
	}

	@Override
	public E next() {
		try {
			return transform(tabs);
		} catch (Exception x) {
			x.printStackTrace(); //???
			return null;
		}
	}

	@Override
	public void remove() {
	}
	protected abstract E transform(String[] tabs) throws Exception;
	
	protected ColumnHeader<Resource>[] readHeader() throws Exception {
		if (count>0) return null;
		String line = reader.readLine();
		String[] h = line.split("\t");
		header = new ColumnHeader[h.length];
		for (int i=0;i< header.length;i++)
			header[i] = new ColumnHeader(h[i],i);
		return header;
	}
	
}
