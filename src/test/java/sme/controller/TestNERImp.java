/**
 * 
 */
package sme.controller;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * Class for test cases for NERImp class
 * @author Andrés Motavita
 *
 */
public class TestNERImp {

	/**
	 * Test method for {@link sme.controller.NERImp#getMatches(java.lang.String)}.
	 */
	@Test
	public void testGetMatchesEmpty() {
		String empty = "";
		List<String> emptyList = new LinkedList<>();
		List<String> list;
		try {
			list = NERImp.getMatches(empty);
			assertArrayEquals("Empty List", list.toArray(), emptyList.toArray());
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Test method for {@link sme.controller.NERImp#getMatches(java.lang.String)}.
	 */
	@Test
	public void testGetMatches() {
		String in = "I go to school at Stanford University, which is located in California.";
		String[] a = {"Stanford University", "California"};
		List<String> list;
		try {
			list = NERImp.getMatches(in);
			assertArrayEquals("Result List", list.toArray(), a);
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
