package ge;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVParser {
	@Autowired
	protected GEProperties properties;

	protected int findNextComma (String s, int p) {
		int res;

		for (;;) {
			res = s.indexOf ('"', p + 1);
			if (s.charAt (res + 1) == ',') {
				return res + 1;
			}
			p = res;
		}
	}

	protected String [] parseLine (String line) {
		String [] ss = new String [5];
		int p1 = 0;
		int p2;
		int index = 0;
		int ssIndex = 0;

		while (ssIndex < ss.length) {
			if (line.charAt (p1) != '"') {
				p2 = line.indexOf (',', p1);
			}
			else {
				p2 = findNextComma (line, p1);
			}
			if ((index >=0 && index <= 3) || index == 22) {
				ss  [ssIndex ++] = line.substring (p1, p2);
			}
			p1 = p2 + 1;
			index ++;
		}
		return ss;
	}

	public void parse (InputStream is)
	throws IOException {
		String s;
		String [] ss;
		BufferedReader br = new BufferedReader (new InputStreamReader (is));

		// skip header
		br.readLine ();
		for (;;) {
			s = br.readLine ();
			if (s == null) {
				break;
			}
			ss = parseLine (s);
			org.merkury.io.JavaObjectWriter.dump (ss);
		}
	}

	@PostConstruct
	public void parseFile () {
		InputStream is;

		try {
			System.out.println (properties.getFile ());
			is = new FileInputStream (properties.getFile ());
			//parse (is);
		}
		catch (Throwable t) {
			throw new Error (t);
		}
	}
}
