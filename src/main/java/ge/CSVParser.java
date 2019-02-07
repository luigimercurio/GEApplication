package ge;

import ge.models.Property;
import ge.models.data.PropertyDao;
import org.merkury.io.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
	@Autowired
	protected GEProperties properties;

	@Autowired
	protected PropertyDao dao;

	protected int findNextComma (String s, int p) {
		int res;

		for (; ; ) {
			res = s.indexOf ('"', p + 1);
			if (s.charAt (res + 1) == ',') {
				return res + 1;
			}
			p = res;
		}
	}

	protected String[] parseLine (String line) {
		String[] ss      = new String[5];
		int      p1      = 0;
		int      p2;
		int      index   = 0;
		int      ssIndex = 0;

		while (ssIndex < ss.length) {
			if (line.charAt (p1) != '"') {
				p2 = line.indexOf (',', p1);
			}
			else {
				p2 = findNextComma (line, p1);
			}
			if ((index >= 0 && index <= 3) || index == 22) {
				ss[ssIndex] = line.substring (p1, p2);
				if (ss[ssIndex].length () == 0) {
					ss[ssIndex] = null;
				}
				ssIndex++;
			}
			p1 = p2 + 1;
			index++;
		}
		return ss;
	}

	public void parse (InputStream is)
	throws IOException {
		String         s;
		String[]       ss;
		BufferedReader br = new BufferedReader (new InputStreamReader (is));
		int            counter;
		List<Property> properties;

		counter = 0;
		// skip header
		br.readLine ();
		properties = new ArrayList<> (100_000);
		for (; ; ) {
			s = br.readLine ();
			if (s == null) {
				break;
			}
			ss = parseLine (s);
			properties.add (
					new Property (Integer.parseInt (ss[2]), Double.parseDouble (ss[0]), Double.parseDouble (ss[1]),
					              ss[3], ss[4]));
			/*
			if (ss[4].length () > 0) {
				org.merkury.io.JavaObjectWriter.dump (ss);
			}
			*/
			counter++;
			if (counter % 100_000 == 0) {
				dao.save (properties);
				GE.logger.info ("Loaded " + counter + " records");
				properties.clear ();
			}
		}
		dao.save (properties);
		GE.logger.info ("Finished loading " + counter + " records");
	}

	@PostConstruct
	public void parseFile () {
		InputStream is = null;

		if (dao.count () != 0) {
			return;
		}
		try {
			//System.out.println (properties.getFile ());
			is = new FileInputStream (properties.getFile ());
			parse (is);
		}
		catch (Throwable t) {
			throw new Error (t);
		}
		finally {
			IOUtil.close (is);
		}
	}
}
