package com.example.batchprocessing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestAzureBlobStoreFileItemReader {

	@Spy
	private AzureBlobStoreFileItemReader<Person> itemReader = new AzureBlobStoreFileItemReader<Person>();

	private static String fileContents1 = "Martin,Newsted,12/06/65\nJohn,Smith,01/01/10";
	private static String fileContents2 = "Martin,Newsted,12/06/65\nJohn,Smith,01/01/10\n";
	private static String fileContents3 = "Martin,Newsted,12/06/65\nJohn,Smith01/01/10";

	@Test
	public void testRead1() {

		Reader dummyInputStream = new TestReader(fileContents1);
		List<Person> results = new ArrayList<Person>();

		when(itemReader.getReader()).thenReturn(dummyInputStream);
		try {
			itemReader.setRowMapper(new FilePersonMapper(","));
			itemReader.afterPropertiesSet();

			Person p;
			while ((p = itemReader.read()) != null) {
				results.add(p);
			}

			assertEquals(2, results.size());
			assertEquals("Martin", results.get(0).getName());
			assertEquals("John", results.get(1).getName());

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRead2() {

		Reader dummyInputStream = new TestReader(fileContents2);
		List<Person> results = new ArrayList<Person>();

		when(itemReader.getReader()).thenReturn(dummyInputStream);
		try {
			itemReader.setRowMapper(new FilePersonMapper(","));
			itemReader.afterPropertiesSet();

			Person p;
			while ((p = itemReader.read()) != null) {
				results.add(p);
			}

			assertEquals(2, results.size());
			assertEquals("Martin", results.get(0).getName());
			assertEquals("John", results.get(1).getName());

		} catch (Exception e) {
			fail();
		}
	}

	@Rule
	public ExpectedException rule = ExpectedException.none();
	
	@Test
	public void testRead3() throws Exception {

		rule.expect(ParseException.class);
		rule.expectMessage("not able to create a Person objec");
		Reader dummyInputStream = new TestReader(fileContents3);
		List<Person> results = new ArrayList<Person>();

		when(itemReader.getReader()).thenReturn(dummyInputStream);
		itemReader.setRowMapper(new FilePersonMapper(","));
		itemReader.afterPropertiesSet();

		Person p;
		while ((p = itemReader.read()) != null) {
			results.add(p);
		}

		assertEquals(2, results.size());
		assertEquals("Martin", results.get(0).getName());
		assertEquals("John", results.get(1).getName());

	}
}

class TestReader extends Reader {

	private String contents;

	public TestReader(String contents) {
		this.contents = contents;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (off >= contents.length())
			return -1;
		cbuf[0] = contents.charAt(off);
		return 1;
	}

}
