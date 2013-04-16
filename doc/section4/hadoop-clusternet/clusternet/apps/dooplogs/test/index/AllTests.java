// (c) Copyright 2009 Cloudera, Inc.
// Updated by Marcello de Sales (marcello.dsales@gmail.com)
package index;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All tests for inverted index code
 *
 * @author aaron
 */
public final class AllTests  {

  private AllTests() { }

  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for the indexer");

    suite.addTestSuite(MapperTest.class);
    suite.addTestSuite(ReducerTest.class);

    return suite;
  }

}

