package com.lj.app.core.common.generator.provider.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lj.app.core.common.generator.provider.db.model.Table;

public class DbTableFactoryTest {

	private  DbTableFactory  dbTableFactory;
	
	@Before
	public void setUp() {
		dbTableFactory =  DbTableFactory.getInstance();
	}
	
	@Test
	public void getInstanceTest() {
		DbTableFactory  dbTableFactory2 = DbTableFactory.getInstance();
		DbTableFactory  dbTableFactory3 = DbTableFactory.getInstance();
		assertSame(dbTableFactory2,dbTableFactory3);
	}

	@Test
	public void getCatalogTest() {
		assertNull(dbTableFactory.getCatalog());
	}

	@Test
	public void getSchemaTest() {
		assertTrue(dbTableFactory.getSchema()==null ||dbTableFactory.getSchema().equals("testSchema") );
	}

	@Test
	public void getConnectionTest() throws Exception {
		Connection connection = dbTableFactory.getConnection();
		assertNotNull(connection);
	}

	@Test
	public void getAllTablesTest() throws Exception {
		List list = dbTableFactory.getAllTables();
		assertNotNull(list);
		
	}

	@Test
	public void getTableTest() throws Exception{
		Table table = dbTableFactory.getTable("UPM_USER");
		assertNotNull(table);
	}
	
	@Test(expected=RuntimeException.class)
	public void getTableNotExitsTest() throws Exception{
		Table table = dbTableFactory.getTable("tableNotExits");
		assertNull(table);
	}

}
