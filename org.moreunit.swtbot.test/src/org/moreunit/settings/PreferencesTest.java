package org.moreunit.settings;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.Test;
import org.moreunit.JavaProjectSWTBotTestHelper;
import org.moreunit.preferences.PreferenceConstants;
import org.moreunit.preferences.Preferences;
import org.moreunit.test.context.Context;

@Context(mainCls="org:HelloWorld")
public class PreferencesTest extends JavaProjectSWTBotTestHelper
{
	private void openPreferencesAndSelectMoreUnitPage()
	{
		getShortcutStrategy().openPreferences();
		bot.tree().select("MoreUnit");
	}
	
	@Test
	public void should_update_test_source_folder_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		SWTBotText sourceFolderTextField = bot.textWithLabel(PreferenceConstants.TEXT_TEST_SOURCE_FOLDER);
		sourceFolderTextField.setText("unittest");
		saveAndClosePrefs();
		
		String junitDirectoryFromPreferences = Preferences.getInstance().getJunitDirectoryFromPreferences(getJavaProjectFromContext());
		assertThat(junitDirectoryFromPreferences).isEqualTo("unittest");
	}

	private void saveAndClosePrefs() 
	{
		bot.button("OK").click();
	}
	
	@Test
	public void should_update_test_type_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.radio(PreferenceConstants.TEXT_JUNIT_3_8).click();
		saveAndClosePrefs();
		String testType = Preferences.getInstance().getTestType(getJavaProjectFromContext());
		assertThat(testType).isEqualTo(PreferenceConstants.TEST_TYPE_VALUE_JUNIT_3);
		
		openPreferencesAndSelectMoreUnitPage();
		bot.radio(PreferenceConstants.TEXT_JUNIT_4).click();
		saveAndClosePrefs();
		testType = Preferences.getInstance().getTestType(getJavaProjectFromContext());
		assertThat(testType).isEqualTo(PreferenceConstants.TEST_TYPE_VALUE_JUNIT_4);
		
		openPreferencesAndSelectMoreUnitPage();
		bot.radio(PreferenceConstants.TEXT_TEST_NG).click();
		saveAndClosePrefs();
		testType = Preferences.getInstance().getTestType(getJavaProjectFromContext());
		assertThat(testType).isEqualTo(PreferenceConstants.TEST_TYPE_VALUE_TESTNG);
	}
	
	@Test
	public void should_update_test_method_prefix_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.radio(PreferenceConstants.TEXT_JUNIT_4).click();
		bot.checkBox(PreferenceConstants.TEXT_TEST_METHOD_TYPE).select();
		saveAndClosePrefs();
		String testMethodType = Preferences.getInstance().getTestMethodType(getJavaProjectFromContext());
		assertThat(testMethodType).isEqualTo(PreferenceConstants.TEST_METHOD_TYPE_JUNIT3);
		
		openPreferencesAndSelectMoreUnitPage();
		bot.checkBox(PreferenceConstants.TEXT_TEST_METHOD_TYPE).deselect();
		saveAndClosePrefs();
		testMethodType = Preferences.getInstance().getTestMethodType(getJavaProjectFromContext());
		assertThat(testMethodType).isEqualTo(PreferenceConstants.TEST_METHOD_TYPE_NO_PREFIX);
	}
	
	@Test
	public void should_update_test_method_content_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.textWithLabel(PreferenceConstants.TEXT_TEST_METHOD_CONTENT).setText("blubbContent");
		saveAndClosePrefs();
		String testMethodDefaultContent = Preferences.getInstance().getTestMethodDefaultContent(getJavaProjectFromContext());
		assertThat(testMethodDefaultContent).isEqualTo("blubbContent");
	}
	
	@Test
	public void should_update_test_prefixes_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.textWithLabel(PreferenceConstants.TEXT_TEST_PREFIXES).setText("Test,ITTest");
		saveAndClosePrefs();
		String[] prefixes = Preferences.getInstance().getPrefixes(getJavaProjectFromContext());
		assertThat(prefixes).containsOnly("Test", "ITTest");
	}
	
	@Test
	public void should_update_test_suffixes_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.textWithLabel(PreferenceConstants.TEXT_TEST_SUFFIXES).setText("Pest,ITPest");
		saveAndClosePrefs();
		String[] suffixes = Preferences.getInstance().getSuffixes(getJavaProjectFromContext());
		assertThat(suffixes).containsOnly("Pest", "ITPest");
	}
	
	@Test
	public void should_update_test_package_prefix_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.textWithLabel(PreferenceConstants.TEXT_PACKAGE_PREFIX).setText("pckgprefix");
		saveAndClosePrefs();
		String testPackagePrefix = Preferences.getInstance().getTestPackagePrefix(getJavaProjectFromContext());
		assertThat(testPackagePrefix).isEqualTo("pckgprefix");
	}
	
	@Test
	public void should_update_test_package_suffix_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.textWithLabel(PreferenceConstants.TEXT_PACKAGE_SUFFIX).setText("pckgsuffix");
		saveAndClosePrefs();
		String testPackageSuffix = Preferences.getInstance().getTestPackageSuffix(getJavaProjectFromContext());
		assertThat(testPackageSuffix).isEqualTo("pckgsuffix");
	}
	
	@Test
	public void should_update_test_superclass_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.textWithLabel(PreferenceConstants.TEXT_TEST_SUPERCLASS).setText("org.moreunit.SuperClass");
		saveAndClosePrefs();
		String testSuperClass = Preferences.getInstance().getTestSuperClass(getJavaProjectFromContext());
		assertThat(testSuperClass).isEqualTo("org.moreunit.SuperClass");
	}
	
	@Test
	public void should_enable_flexible_naming_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.checkBox(PreferenceConstants.TEXT_FLEXIBLE_NAMING).select();
		saveAndClosePrefs();
		assertTrue(Preferences.getInstance().shouldUseFlexibleTestCaseNaming(getJavaProjectFromContext()));
		
		openPreferencesAndSelectMoreUnitPage();
		bot.checkBox(PreferenceConstants.TEXT_FLEXIBLE_NAMING).deselect();
		saveAndClosePrefs();
		assertFalse(Preferences.getInstance().shouldUseFlexibleTestCaseNaming(getJavaProjectFromContext()));
	}
	
	@Test
	public void should_enable_extended_method_search_when_preferences_change()
	{
		openPreferencesAndSelectMoreUnitPage();
		bot.checkBox(PreferenceConstants.TEXT_EXTENDED_TEST_METHOD_SEARCH).select();
		saveAndClosePrefs();
		assertTrue(Preferences.getInstance().shouldUseTestMethodExtendedSearch(getJavaProjectFromContext()));
		
		openPreferencesAndSelectMoreUnitPage();
		bot.checkBox(PreferenceConstants.TEXT_EXTENDED_TEST_METHOD_SEARCH).deselect();
		saveAndClosePrefs();
		assertFalse(Preferences.getInstance().shouldUseTestMethodExtendedSearch(getJavaProjectFromContext()));
	}
}
