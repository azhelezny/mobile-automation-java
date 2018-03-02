package framework.reporter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Zhelezny
 *         Date: 9/9/15
 */
public class HtmlCreator {

    public static List<String> getTestCssFile() {
        List<String> lt = new ArrayList<String>();
        lt.add("h3.testName");
        lt.add("{ text-align: center; }");
        lt.add("#failed");
        lt.add("{ color: red;}");
        lt.add("#passed");
        lt.add("{ color: green;}");
        lt.add("#skipped");
        lt.add("{ color: grey;}");
        lt.add("div.invisible");
        lt.add("{ display: none;}");
        lt.add("table.testResults");
        lt.add("{");
        lt.add("    width: 100%;");
        lt.add("    border-top-width: 2px;");
        lt.add("    border-color: black;");
        lt.add("    border-top-style: solid;");
        lt.add("    border collapse: collapse;");
        lt.add("}");
        lt.add("div.testTdException");
        lt.add("{ color: red; }");
        lt.add("pre");
        lt.add("{");
        lt.add("line-height: 13px;");
        lt.add("margin: 0;");
        lt.add("padding: 0;");
        lt.add("}");
        return lt;
    }

    public static List<String> getClassCssFile() {
        List<String> lt = new ArrayList<String>();
        lt.add("#failed");
        lt.add("{ color: red;}");
        lt.add("#passed");
        lt.add("{ color: green;}");
        lt.add("#skipped");
        lt.add("{ color: grey;}");
        lt.add("div.className");
        lt.add("{ text-align: left; }");
        lt.add("table.clear");
        lt.add("{");
        lt.add("    width: 100%;");
        lt.add("    border-bottom-width: 2px;");
        lt.add("    border-bottom-style: solid;");
        lt.add("    border-top-width: 2px;");
        lt.add("    border-top-style: solid;");
        lt.add("    border-color: black;");
        lt.add("}");
        lt.add("th");
        lt.add("{");
        lt.add("    text-align: center;");
        lt.add("    background-color: #B0C4DE;");
        lt.add("}");
        lt.add("td.topLine");
        lt.add("{");
        lt.add("    border-top-width: 1px;");
        lt.add("    border-top-style: solid;");
        lt.add("}");
        lt.add("td.topBottomLines");
        lt.add("{");
        lt.add("    border-bottom-width: 1px;");
        lt.add("    border-bottom-style: solid;");
        lt.add("    border-top-width: 1px;");
        lt.add("    border-top-style: solid;");
        lt.add("}");
        lt.add("td.testName");
        lt.add("{");
        lt.add("    width: 90%;");
        lt.add("    text-align: left;");
        lt.add("}");
        lt.add("td.configOrTest");
        lt.add("{");
        lt.add("    width: 1%;");
        lt.add("    white-space: nowrap;");
        lt.add("    text-align: left;");
        lt.add("}");
        return lt;
    }

    public static List<String> getMainCssFile() {
        List<String> lt = new ArrayList<String>();
        lt.add("#failed");
        lt.add("{ color: red;}");
        lt.add("#passed");
        lt.add("{ color: green;}");
        lt.add("#skipped");
        lt.add("{ color: grey;}");
        lt.add("table.clear");
        lt.add("{");
        lt.add("    width: 100%;");
        lt.add("    border-bottom-width: 2px;");
        lt.add("    border-bottom-style: solid;");
        lt.add("    border-top-width: 2px;");
        lt.add("    border-top-style: solid;");
        lt.add("    border-color: black;");
        lt.add("}");
        lt.add("th");
        lt.add("{");
        lt.add("    text-align: center;");
        lt.add("    background-color: #B0C4DE;");
        lt.add("}");
        lt.add("td.suiteName");
        lt.add("{");
        lt.add("    text-align: left;");
        lt.add("    width: 1%;");
        lt.add("    white-space: nowrap;");
        lt.add("    border-bottom-width: 1px;");
        lt.add("    border-bottom-style: dotted;");
        lt.add("}");
        lt.add("td.pkgName");
        lt.add("{");
        lt.add("    text-align: left;");
        lt.add("    width: 1%;");
        lt.add("    white-space: nowrap;");
        lt.add("    border-bottom-width: 1px;");
        lt.add("    border-bottom-style: dotted;");
        lt.add("}");
        lt.add("td.className");
        lt.add("{");
        lt.add("    text-align: left;");
        lt.add("    border-bottom-width: 1px;");
        lt.add("    border-bottom-style: dotted;");
        lt.add("}");
        lt.add("td.subTests");
        lt.add("{");
        lt.add("    width: 10%;");
        lt.add("    text-align: left;");
        lt.add("}");
        lt.add("td.duration");
        lt.add("{");
        lt.add("    width: 15%;");
        lt.add("    text-align: left;");
        lt.add("}");
        lt.add("td.result");
        lt.add("{");
        lt.add("    width: 5%;");
        lt.add("    text-align: left;");
        lt.add("}");
        return lt;
    }
}
