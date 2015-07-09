package data;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utilities.Settings;

public class SettingsTest {

	private Settings testSettings;

	@Before
	public void setup() throws Exception {
		testSettings = new Settings("src/main/test/test_settings.properties");
	}

	@Test
	public void checkReadCompleteFile() throws Exception {
		Properties settings = Settings.getSettings();
		Enumeration e = settings.propertyNames();
		Enumeration<?> testPropertyNames = testSettings.propertyNames();
		Assert.assertTrue(Collections.list(testPropertyNames).size() == Collections
				.list(e).size());
	}

	@Test
	public void readProp_returnCorrectValue_forKey() throws Exception {
		String p;

		Properties settings = Settings.getSettings();
		Enumeration e = settings.propertyNames();

		p = settings.getProperty("tournamentsize");
		assertEquals("2", p);
	}

	@Test
	public void checkif_valuesAreSame() throws Exception {
		Properties settings = Settings.getSettings();
		List<?> properties = Collections.list(settings.propertyNames());
		List<?> testPropertyNames = Collections.list(testSettings
				.propertyNames());
		for (int i = 0; i <= 4; i++) {

			String key = (String) properties.get(i);
			String testkey = (String) testPropertyNames.get(i);

			assertEquals(settings.getProperty(testkey),
					settings.getProperty(key));
		}
	}
}
