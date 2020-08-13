package ru.otus.homework.tests;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;
import ru.otus.homework.calssfortest.StringUtil;
import ru.otus.homework.fortest.AHomeWorkTest;

public class StringUtilTest extends AHomeWorkTest {

    StringUtil util = null;
    @Before
    @Override
    public void setUp() {
        util = new StringUtil();
    }
    @After
    @Override
    public void tearDown() {
        util = null;
    }

    @Test
    public void onlyNumbersTest() throws Exception {
        assertString(util.giveOnlyNumbers("ag11ds3k5"), "1135");
        assertString(util.giveOnlyNumbers("5d4f6s5ad"), "5465");
        assertString(util.giveOnlyNumbers("4dd2h8hjkl5"), "4285");
    }

    @Test
    public void shortNameTest() throws Exception {
        assertString(util.createShortName("Иванов", "Иван", "Иванович"), "Иванов И.И.");
        assertString(util.createShortName("Петров", "Петр", "Петрович"), "Петров П.П.");
    }

    @Test
    public void shortNameUpperTest() throws Exception {
        assertString(util.createShortName("иванов", "иван", "иванович"), "Иванов И.И.");
        assertString(util.createShortName("петров", "петр", "петрович"), "Петров П.П.");
    }

    @Test
    public void errorTest() throws Exception {
        assertString(util.createShortName("иванов", "иван", "иванович"), "Петров П.П.");
    }
}
