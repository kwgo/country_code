package com.jchip.country.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MainHelper {

    public static final int COUNTRY = 0;
    public static final int OFFICIAL = 1;
    public static final int SOVEREIGNTY = 2;
    public static final int ALPHA_2 = 3;
    public static final int ALPHA_3 = 4;
    public static final int NUMERIC = 5;
    public static final int INTERNET = 6;
    public static final int CAPITAL = 7;
    public static final int TIMEZONE = 8;
    public static final int CALL_CODE = 9;
    public static final int CURRENCY = 10;
    public static final int SYMBOL = 11;
    public static final int CURRENCY_NAME = 12;
    public static final int FRACTION = 13;
    public static final int BASIC_NUMBER = 14;
    public static final int COUNTRY_SHORT = 15;
    public static final int FLAG_RATIO = 16;
    public static final int POPULATION = 17;

    public static final int SORT_COUNTRY = 0;
    public static final int SORT_OFFICIAL = 1;
    public static final int SORT_ALPHA_2 = 2;
    public static final int SORT_ALPHA_3 = 3;
    public static final int SORT_NUMERIC = 4;
    public static final int SORT_CURRENCY = 5;
    public static final int SORT_SYMBOL = 6;
    public static final int SORT_CALL_CODE = 7;
    public static final int SORT_TIMEZONE = 8;
    public static final int SORT_CAPITAL = 9;
    public static final int SORT_POPULATION = 10;

    public static final int[] sortIndexes = new int[]{COUNTRY, OFFICIAL, ALPHA_2, ALPHA_3, NUMERIC, CURRENCY, SYMBOL, CALL_CODE, TIMEZONE, CAPITAL, POPULATION};
    public static final int[] searchIndexes = new int[]{ALPHA_2, ALPHA_3, NUMERIC, CURRENCY, CALL_CODE, TIMEZONE, COUNTRY, SYMBOL, OFFICIAL, CAPITAL};
    public static final int[] detailIndexes = new int[]{COUNTRY, OFFICIAL, SOVEREIGNTY, ALPHA_2, ALPHA_3, NUMERIC, INTERNET, CAPITAL, TIMEZONE, CALL_CODE, CURRENCY, CURRENCY_NAME, FRACTION, POPULATION};

    public static boolean isSortable(int sortIndex, boolean isPortrait) {
        if (isPortrait) {
            if (sortIndex == MainHelper.SORT_OFFICIAL || sortIndex == MainHelper.SORT_ALPHA_3 || sortIndex == MainHelper.SORT_NUMERIC || sortIndex == MainHelper.SORT_SYMBOL || sortIndex == MainHelper.SORT_POPULATION) {
                return false;
            }
        } else {
            if (sortIndex == MainHelper.SORT_COUNTRY) {
                return false;
            }
        }
        return true;
    }

    public static List<String> sortCountryInfo(Map<String, String[]> info, List<String> listInfo, int sortIndex) {
        if (sortIndex >= 0) {
            Collections.sort(listInfo, new InfoComparator(info, sortIndexes[sortIndex]));
        }
        return listInfo;
    }

    public static List<String> searchCountryInfo(Map<String, String[]> info, String searchText, boolean isPortrait) {
        List<String> searchInfo = new ArrayList<>();
        if (!searchText.trim().isEmpty()) {
            for (int searchIndex : searchIndexes) {
                for (String item : info.keySet()) {
                    if (!searchInfo.contains(item)) {
                        String[] phases = info.get(item);
                        if (phases[searchIndex].toUpperCase().contains(searchText)) {
                            searchInfo.add(item);
                        }
                    }
                }
            }
        }
        return searchInfo;
    }

    private static class InfoComparator implements Comparator<String> {
        private Map<String, String[]> info;
        private int index;

        public InfoComparator(Map<String, String[]> info, int index) {
            this.info = info;
            this.index = index;
        }

        public int compare(String key1, String key2) {
            if (index == POPULATION) {
                int value1 = MainUtility.parseInteger(info.get(key1)[index], 0);
                int value2 = MainUtility.parseInteger(info.get(key2)[index], 0);
                return value2 - value1;
//            } else if (index == TIMEZONE) {
//                if (info.get(key1)[index].charAt(0) != info.get(key2)[index].charAt(0)) {
//                    return info.get(key1)[index].compareTo(info.get(key2)[index]);
//                } else if (info.get(key1)[index].charAt(0) == '-') {
//                    return info.get(key2)[index].compareTo(info.get(key1)[index]);
//                }
//                return info.get(key1)[index].compareTo(info.get(key2)[index]);
            }
            return info.get(key1)[index].compareTo(info.get(key2)[index]);
        }
    }

    public static String[] getISOHeader() {
        return new String[]{"Country name", "Official name", "Sovereignty", "Alpha-2 code", "Alpha-3 code", "Numeric code", "Internet ccTLD", "Capital", "Timezone", "Call code", "Currency", "Symbol", "Currency name", "Fractional unit", "Basic number", "Country short", "Flag ratio", "Population"};
    }

    public static Map<String, String[]> getISOInfo() {
        Map<String, String[]> info = new HashMap<>();
        info.put("AD", new String[]{"Andorra", "The Principality of Andorra", "UN member state", "AD", "AND", "020", ".ad", "Andorra la Vella", "+01:00", "376", "EUR", "€", "Euro", "Cent", "100", "Andorra", "7:10 (1.429)", "78,015"});
        info.put("AE", new String[]{"United Arab Emirates", "The United Arab Emirates", "UN member state", "AE", "ARE", "784", ".ae", "Abu Dhabi", "+04:00", "971", "AED", "د.إ", "United Arab Emirates dirham", "Fils", "100", "United Arab Emirates", "1:2 (2)", "9,503,738"});
        info.put("AF", new String[]{"Afghanistan", "The Islamic Republic of Afghanistan", "UN member state", "AF", "AFG", "004", ".af", "Kabul", "+04:30", "93", "AFN", "؋", "Afghan afghani", "Pul", "100", "Afghanistan", "2:3 (1.5)", "32,890,171"});
        info.put("AG", new String[]{"Antigua and Barbuda", "Antigua and Barbuda", "UN member state", "AG", "ATG", "028", ".ag", "St. John''s", "-04:00", "1-268", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Antigua and Barbuda", "2:3 (1.5)", "99,337"});
        info.put("AI", new String[]{"Anguilla", "Anguilla", "United Kingdom", "AI", "AIA", "660", ".ai", "The Valley", "-04:00", "1-264", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Anguilla", "1:2 (2)", "15,000"});
        info.put("AL", new String[]{"Albania", "The Republic of Albania", "UN member state", "AL", "ALB", "008", ".al", "Tirana", "+02:00", "355", "ALL", "L", "Albanian lek", "Qindarkë", "100", "Albania", "5:7 (1.4)", "2,829,741"});
        info.put("AM", new String[]{"Armenia", "The Republic of Armenia", "UN member state", "AM", "ARM", "051", ".am", "Yerevan", "+04:00", "374", "AMD", "֏", "Armenian dram", "Luma", "100", "Armenia", "1:2 (2)", "2,963,900"});
        info.put("AO", new String[]{"Angola", "The Republic of Angola", "UN member state", "AO", "AGO", "024", ".ao", "Luanda", "+01:00", "244", "AOA", "Kz", "Angolan kwanza", "Cêntimo", "100", "Angola", "2:3 (1.5)", "32,097,671"});
        info.put("AQ", new String[]{"Antarctica", "All land and ice shelves south of the 60th parallel south", "Antarctic Treaty", "AQ", "ATA", "010", ".aq", "", "+00:00", "213", "ATA", "A$", "Antarctica dollar", "(none)", "(none)", "Antarctica", "2:3 (1.5)", "1,000"});
        info.put("AR", new String[]{"Argentina", "The Argentine Republic", "UN member state", "AR", "ARG", "032", ".ar", "Buenos Aires", "-03:00", "54", "ARS", "$", "Argentine peso", "Centavo", "100", "Argentina", "5:8 (1.6)", "45,808,747"});
        info.put("AS", new String[]{"American Samoa", "The Territory of American Samoa", "United States", "AS", "ASM", "016", ".as", "Pago Pago", "-11:00", "1-684", "USD", "$", "United States dollar", "Cent", "100", "American Samoa", "1:2 (2)", "56,951"});
        info.put("AT", new String[]{"Austria", "The Republic of Austria", "UN member state", "AT", "AUT", "040", ".at", "Vienna", "+01:00", "43", "EUR", "€", "Euro", "Cent", "100", "Austria", "2:3 (1.5)", "8,950,544"});
        info.put("AU", new String[]{"Australia", "The Commonwealth of Australia", "UN member state", "AU", "AUS", "036", ".au", "Canberra", "+10:00", "61", "AUD", "$", "Australian dollar", "Cent", "100", "Australia", "1:2 (2)", "25,885,427"});
        info.put("AW", new String[]{"Aruba", "Aruba", "Netherlands", "AW", "ABW", "533", ".aw", "Oranjestad", "-04:00", "297", "AWG", "ƒ", "Aruban florin", "Cent", "100", "Aruba", "2:3 (1.5)", "111,050"});
        info.put("AX", new String[]{"Åland Islands", "Åland", "Finland", "AX", "ALA", "248", ".ax", "Mariehamn", "+02:00", "358", "EUR", "€", "Euro", "Cent", "100", "Åland", "17:26 (1.529)", "30,313"});
        info.put("AZ", new String[]{"Azerbaijan", "The Republic of Azerbaijan", "UN member state", "AZ", "AZE", "031", ".az", "Baku", "+04:00", "994", "AZN", "₼", "Azerbaijani manat", "Qəpik", "100", "Azerbaijan", "1:2 (2)", "10,139,196"});
        info.put("BA", new String[]{"Bosnia and Herzegovina", "Bosnia and Herzegovina", "UN member state", "BA", "BIH", "070", ".ba", "Sarajevo", "+02:00", "387", "BAM", "KM or КМ", "Bosnia and Herzegovina convertible mark", "Fening", "100", "Bosnia and Herzegovina", "1:2 (2)", "3,320,954"});
        info.put("BB", new String[]{"Barbados", "Barbados", "UN member state", "BB", "BRB", "052", ".bb", "Bridgetown", "-04:00", "1-246", "BBD", "$", "Barbadian dollar", "Cent", "100", "Barbados", "2:3 (1.5)", "288,000"});
        info.put("BD", new String[]{"Bangladesh", "The People's Republic of Bangladesh", "UN member state", "BD", "BGD", "050", ".bd", "Dhaka", "+06:00", "880", "BDT", "৳", "Bangladeshi taka", "Poisha", "100", "Bangladesh", "3:5 (1.667)", "171,567,488"});
        info.put("BE", new String[]{"Belgium", "The Kingdom of Belgium", "UN member state", "BE", "BEL", "056", ".be", "City of Brussels", "+01:00", "32", "EUR", "€", "Euro", "Cent", "100", "Belgium", "13:15 (1.154)", "11,580,185"});
        info.put("BF", new String[]{"Burkina Faso", "Burkina Faso", "UN member state", "BF", "BFA", "854", ".bf", "Ouagadougou", "+00:00", "226", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Burkina Faso", "2:3 (1.5)", "21,510,181"});
        info.put("BG", new String[]{"Bulgaria", "The Republic of Bulgaria", "UN member state", "BG", "BGR", "100", ".bg", "Sofia", "+02:00", "359", "BGN", "лв.", "Bulgarian lev", "Stotinka", "100", "Bulgaria", "3:5 (1.667)", "6,875,040"});
        info.put("BH", new String[]{"Bahrain", "The Kingdom of Bahrain", "UN member state", "BH", "BHR", "048", ".bh", "Manama", "+03:00", "973", "BHD", ".د.ب", "Bahraini dinar", "Fils", "1000", "Bahrain", "3:5 (1.667)", "1,501,635"});
        info.put("BI", new String[]{"Burundi", "The Republic of Burundi", "UN member state", "BI", "BDI", "108", ".bi", "Gitega", "+02:00", "257", "BIF", "Fr", "Burundian franc", "Centime", "100", "Burundi", "3:5 (1.667)", "12,574,571"});
        info.put("BJ", new String[]{"Benin", "The Republic of Benin", "UN member state", "BJ", "BEN", "204", ".bj", "Porto-Novo", "+01:00", "229", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Benin", "2:3 (1.5)", "12,506,347"});
        info.put("BL", new String[]{"Saint Barthélemy", "The Collectivity of Saint-Barthélemy", "France", "BL", "BLM", "652", ".bl", "Gustavia", "-04:00", "590", "EUR", "€", "Euro", "Cent", "100", "Saint Barthelemy", "2:3 (1.5)", "10,124"});
        info.put("BM", new String[]{"Bermuda", "Bermuda", "United Kingdom", "BM", "BMU", "060", ".bm", "Hamilton", "-04:00", "1-441", "BMD", "$", "Bermudian dollar", "Cent", "100", "Bermuda", "1:2 (2)", "64,055"});
        info.put("BN", new String[]{"Brunei Darussalam", "The Nation of Brunei, the Abode of Peace", "UN member state", "BN", "BRN", "096", ".bn", "Bandar Seri Begawan", "+08:00", "673", "BND", "$", "Brunei dollar", "Sen", "100", "Brunei", "1:2 (2)", "453,600"});
        info.put("BO", new String[]{"Bolivia (Plurinational State of)", "The Plurinational State of Bolivia", "UN member state", "BO", "BOL", "068", ".bo", "Sucre", "-04:00", "591", "BOB", "Bs.", "Bolivian boliviano", "Centavo", "100", "Bolivia", "15:22 (1.467)", "11,797,257"});
        info.put("BQ", new String[]{"Bonaire", "Bonaire", "Netherlands", "BQ", "BES", "535", ".nl", "Kralendijk", "-04:00", "599", "USD", "$", "United States dollar", "Cent", "100", "Bonaire (Netherlands)", "2:3 (1.5)", "20,104"});
        info.put("BQ1", new String[]{"Sint Eustatius", "Sint Eustatius", "Netherlands", "BQ", "BES", "535", ".nl", "Oranjestad", "-04:00", "599", "USD", "$", "United States dollar", "Cent", "100", "Sint Eustatius", "2:3 (1.5)", "3,138"});
        info.put("BQ2", new String[]{"Saba", "Saba", "Netherlands", "BQ", "BES", "535", ".nl", "The Bottom", "-04:00", "599", "USD", "$", "United States dollar", "Cent", "100", "Saba", "2:3 (1.5)", "1,933"});
        info.put("BR", new String[]{"Brazil", "The Federative Republic of Brazil", "UN member state", "BR", "BRA", "076", ".br", "Brasília", "-03:00", "55", "BRL", "R$", "Brazilian real", "Centavo", "100", "Brazil", "7:10 (1.429)", "213,843,297"});
        info.put("BS", new String[]{"Bahamas", "The Commonwealth of The Bahamas", "UN member state", "BS", "BHS", "044", ".bs", "Nassau", "-05:00", "1-242", "BSD", "$", "Bahamian dollar", "Cent", "100", "Bahamas", "1:2 (2)", "393,450"});
        info.put("BT", new String[]{"Bhutan", "The Kingdom of Bhutan", "UN member state", "BT", "BTN", "064", ".bt", "Thimphu", "+06:00", "975", "BTN", "Nu.", "Bhutanese ngultrum", "Chetrum", "100", "Bhutan", "2:3 (1.5)", "756,129"});
        info.put("BV", new String[]{"Bouvet Island", "Bouvet Island", "Norway", "BV", "BVT", "074", "(.bv)", "", "+02:00", "55", "NOK", "kr", "Norwegian krone", "Øre", "100", "Norway", "8:11 (1.375)", "5,402,171"});
        info.put("BW", new String[]{"Botswana", "The Republic of Botswana", "UN member state", "BW", "BWA", "072", ".bw", "Gaborone", "+02:00", "267", "BWP", "P", "Botswana pula", "Thebe", "100", "Botswana", "2:3 (1.5)", "2,410,338"});
        info.put("BY", new String[]{"Belarus", "The Republic of Belarus", "UN member state", "BY", "BLR", "112", ".by", "Minsk", "+03:00", "375", "BYN", "Br", "Belarusian ruble", "Kapyeyka", "100", "Belarus", "1:2 (2)", "9,349,645"});
        info.put("BZ", new String[]{"Belize", "Belize", "UN member state", "BZ", "BLZ", "084", ".bz", "Belmopan", "-06:00", "501", "BZD", "$", "Belize dollar", "Cent", "100", "Belize", "3:5 (1.667)", "430,191"});
        info.put("CA", new String[]{"Canada", "Canada", "UN member state", "CA", "CAN", "124", ".ca", "Ottawa", "-03:00", "1", "CAD", "$", "Canadian dollar", "Cent", "100", "Canada", "1:2 (2)", "38,448,356"});
        info.put("CC", new String[]{"Cocos (Keeling) Islands", "The Territory of Cocos (Keeling) Islands", "Australia", "CC", "CCK", "166", ".cc", "West Island", "+06:30", "61", "AUD", "$", "Australian dollar", "Cent", "100", "Cocos (Keeling) Islands", "1:2 (2)", "573"});
        //info.put("CD", new String[]{"Congo (the Democratic Republic of the)", "The Democratic Republic of the Congo", "UN member state", "CD", "COD", "180", ".cd", "Kinshasa", "+01:00", "243", "CDF", "Fr", "Congolese franc", "Centime", "100", "Dem. Rep. of the Congo", "3:4 (1.333)", "92,378,000"});
        info.put("CD", new String[]{"Congo, Democratic Republic of the", "The Democratic Republic of the Congo", "UN member state", "CD", "COD", "180", ".cd", "Kinshasa", "+01:00", "243", "CDF", "Fr", "Congolese franc", "Centime", "100", "Dem. Rep. of the Congo", "3:4 (1.333)", "92,378,000"});
        info.put("CF", new String[]{"Central African Republic", "The Central African Republic", "UN member state", "CF", "CAF", "140", ".cf", "Bangui", "+01:00", "236", "XAF", "Fr", "Central African CFA franc", "Centime", "100", "Central African Republic", "2:3 (1.5)", "5,633,412"});
        info.put("CG", new String[]{"Congo", "The Republic of the Congo", "UN member state", "CG", "COG", "178", ".cg", "Brazzaville", "+01:00", "242", "XAF", "Fr", "Central African CFA franc", "Centime", "100", "Republic of the Congo", "2:3 (1.5)", "5,657,000"});
        info.put("CH", new String[]{"Switzerland", "The Swiss Confederation", "UN member state", "CH", "CHE", "756", ".ch", "Bern", "+01:00", "41", "CHF", "Fr.", "Swiss franc", "Centime", "100", "Switzerland", "1:1 (1)", "8,696,088"});
        info.put("CI", new String[]{"Côte d'Ivoire", "The Republic of Côte d'Ivoire", "UN member state", "CI", "CIV", "384", ".ci", "Yamoussoukro", "+00:00", "225", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Ivory Coast", "2:3 (1.5)", "27,087,732"});
        info.put("CK", new String[]{"Cook Islands", "The Cook Islands", "New Zealand", "CK", "COK", "184", ".ck", "Avarua", "-10:00", "682", "NZD", "$", "New Zealand dollar", "Cent", "100", "Cook Islands", "1:2 (2)", "15,342"});
        info.put("CL", new String[]{"Chile", "The Republic of Chile", "UN member state", "CL", "CHL", "152", ".cl", "Santiago", "-03:00", "56", "CLP", "$", "Chilean peso", "Centavo", "100", "Chile", "2:3 (1.5)", "19,678,363"});
        info.put("CM", new String[]{"Cameroon", "The Republic of Cameroon", "UN member state", "CM", "CMR", "120", ".cm", "Yaoundé", "+01:00", "237", "XAF", "Fr", "Central African CFA franc", "Centime", "100", "Cameroon", "2:3 (1.5)", "24,348,251"});
        info.put("CN", new String[]{"China", "The People's Republic of China", "UN member state", "CN", "CHN", "156", ".cn", "Beijing", "+08:00", "86", "CNY", "¥ or 元", "Chinese yuan", "Jiao", "10", "China", "2:3 (1.5)", "1,411,778,724"});
        info.put("CO", new String[]{"Colombia", "The Republic of Colombia", "UN member state", "CO", "COL", "170", ".co", "Bogotá", "-05:00", "57", "COP", "$", "Colombian peso", "Centavo", "100", "Colombia", "2:3 (1.5)", "51,049,498"});
        info.put("CR", new String[]{"Costa Rica", "The Republic of Costa Rica", "UN member state", "CR", "CRI", "188", ".cr", "San Jose", "-06:00", "506", "CRC", "₡", "Costa Rican colón", "Céntimo", "100", "Costa Rica", "3:5 (1.667)", "5,163,038"});
        info.put("CU", new String[]{"Cuba", "The Republic of Cuba", "UN member state", "CU", "CUB", "192", ".cu", "Havana", "-05:00", "53", "CUP", "$", "Cuban peso", "Centavo", "100", "Cuba", "1:2 (2)", "11,181,595"});
        info.put("CV", new String[]{"Cabo Verde", "The Republic of Cabo Verde", "UN member state", "CV", "CPV", "132", ".cv", "Praia", "-01:00", "238", "CVE", "Esc or $", "Cape Verdean escudo", "Centavo", "100", "Cape Verde", "3:5 (1.667)", "563,198"});
        info.put("CW", new String[]{"Curaçao", "The Country of Curaçao", "Netherlands", "CW", "CUW", "531", ".cw", "Willemstad", "-04:00", "599", "ANG", "ƒ", "Netherlands Antillean guilder", "Cent", "100", "Curaçao", "2:3 (1.5)", "153,671"});
        info.put("CX", new String[]{"Christmas Island", "The Territory of Christmas Island", "Australia", "CX", "CXR", "162", ".cx", "Flying Fish Cove", "+07:00", "61", "AUD", "$", "Australian dollar", "Cent", "100", "Christmas Island", "1:2 (2)", "1,966"});
        info.put("CY", new String[]{"Cyprus", "The Republic of Cyprus", "UN member state", "CY", "CYP", "196", ".cy", "Nicosia", "+02:00", "357", "EUR", "€", "Euro", "Cent", "100", "Cyprus", "2:3 (1.5)", "888,005"});
        info.put("CZ", new String[]{"Czechia", "The Czech Republic", "UN member state", "CZ", "CZE", "203", ".cz", "Prague", "+02:00", "420", "CZK", "Kč", "Czech koruna", "Haléř", "100", "Czech Republic", "2:3 (1.5)", "10,702,942"});
        info.put("DE", new String[]{"Germany", "The Federal Republic of Germany", "UN member state", "DE", "DEU", "276", ".de", "Berlin", "+01:00", "49", "EUR", "€", "Euro", "Cent", "100", "Germany", "3:5 (1.667)", "83,129,285"});
        info.put("DJ", new String[]{"Djibouti", "The Republic of Djibouti", "UN member state", "DJ", "DJI", "262", ".dj", "Djibouti", "+03:00", "253", "DJF", "Fr", "Djiboutian franc", "Centime", "100", "Djibouti", "2:3 (1.5)", "976,107"});
        info.put("DK", new String[]{"Denmark", "The Kingdom of Denmark", "UN member state", "DK", "DNK", "208", ".dk", "Copenhagen", "+01:00", "45", "DKK", "kr", "Danish krone", "Øre", "100", "Denmark", "28:37 (1.321)", "5,850,189"});
        info.put("DM", new String[]{"Dominica", "The Commonwealth of Dominica", "UN member state", "DM", "DMA", "212", ".dm", "Roseau", "-04:00", "1-767", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Dominica", "1:2 (2)", "72,000"});
        info.put("DO", new String[]{"Dominican Republic", "The Dominican Republic", "UN member state", "DO", "DOM", "214", ".do", "Santo Domingo", "-04:00", "1-809", "DOP", "RD$", "Dominican peso", "Centavo", "100", "Dominican Republic", "5:8 (1.6)", "10,535,535"});
        info.put("DZ", new String[]{"Algeria", "The People's Democratic Republic of Algeria", "UN member state", "DZ", "DZA", "012", ".dz", "Algiers", "+01:00", "213", "DZD", "د.ج", "Algerian dinar", "Santeem", "100", "Algeria", "2:3 (1.5)", "44,700,000"});
        info.put("EC", new String[]{"Ecuador", "The Republic of Ecuador", "UN member state", "EC", "ECU", "218", ".ec", "Quito", "-05:00", "593", "USD", "$", "United States dollar", "Cent", "100", "Ecuador", "2:3 (1.5)", "17,840,692"});
        info.put("EE", new String[]{"Estonia", "The Republic of Estonia", "UN member state", "EE", "EST", "233", ".ee", "Tallinn", "+02:00", "372", "EUR", "€", "Euro", "Cent", "100", "Estonia", "7:11 (1.571)", "1,330,068"});
        info.put("EG", new String[]{"Egypt", "The Arab Republic of Egypt", "UN member state", "EG", "EGY", "818", ".eg", "Cairo", "+02:00", "20", "EGP", "£ or ج.م", "Egyptian pound", "Piastre", "100", "Egypt", "2:3 (1.5)", "102,470,591"});
        info.put("EH", new String[]{"Western Sahara", "The Sahrawi Arab Democratic Republic", "Disputed", "EH", "ESH", "732", "(.eh)", "Western Sahara", "+01:00", "212", "MAD", "د.م.", "Moroccan dirham", "Centime", "100", "Western Sahara", "1:2 (2)", "612,000"});
        info.put("ER", new String[]{"Eritrea", "The State of Eritrea", "UN member state", "ER", "ERI", "232", ".er", "Asmara", "+03:00", "291", "ERN", "Nfk", "Eritrean nakfa", "Cent", "100", "Eritrea", "1:2 (2)", "3,601,000"});
        info.put("ES", new String[]{"Spain", "The Kingdom of Spain", "UN member state", "ES", "ESP", "724", ".es", "Madrid", "+01:00", "34", "EUR", "€", "Euro", "Cent", "100", "Spain", "2:3 (1.5)", "47,394,223"});
        info.put("ET", new String[]{"Ethiopia", "The Federal Democratic Republic of Ethiopia", "UN member state", "ET", "ETH", "231", ".et", "Addis Ababa", "+03:00", "251", "ETB", "Br", "Ethiopian birr", "Santim", "100", "Ethiopia", "1:2 (2)", "117,876,000"});
        info.put("FI", new String[]{"Finland", "The Republic of Finland", "UN member state", "FI", "FIN", "246", ".fi", "Helsinki", "+03:00", "358", "EUR", "€", "Euro", "Cent", "100", "Finland", "11:18 (1.636)", "5,516,184"});
        info.put("FJ", new String[]{"Fiji", "The Republic of Fiji", "UN member state", "FJ", "FJI", "242", ".fj", "Suva", "+12:00", "679", "FJD", "$", "Fijian dollar", "Cent", "100", "Fiji", "1:2 (2)", "898,402"});
        info.put("FK", new String[]{"Falkland Islands (the) [Malvinas]", "The Falkland Islands", "United Kingdom", "FK", "FLK", "238", ".fk", "Stanley", "-03:00", "500", "FKP", "£", "Falkland Islands pound", "Penny", "100", "Falkland Islands", "1:2 (2)", "4,000"});
        info.put("FM", new String[]{"Micronesia (Federated States of)", "The Federated States of Micronesia", "UN member state", "FM", "FSM", "583", ".fm", "Palikir", "+11:00", "691", "USD", "$", "United States dollar", "Cent", "100", "Micronesia", "10:19 (1.9)", "105,754"});
        info.put("FO", new String[]{"Faroe Islands", "The Faroe Islands", "Denmark", "FO", "FRO", "234", ".fo", "Tórshavn", "+00:00", "298", "DKK", "kr", "Danish krone", "Øre", "100", "Faroe Islands", "8:11 (1.375)", "53,498"});
        info.put("FR", new String[]{"France", "The French Republic", "UN member state", "FR", "FRA", "250", ".fr", "Paris", "+01:00", "33", "EUR", "€", "Euro", "Cent", "100", "France", "2:3 (1.5)", "67,486,000"});
        info.put("GA", new String[]{"Gabon", "The Gabonese Republic", "UN member state", "GA", "GAB", "266", ".ga", "Libreville", "+01:00", "241", "XAF", "Fr", "Central African CFA franc", "Centime", "100", "Gabon", "3:4 (1.333)", "2,233,272"});
        info.put("GB", new String[]{"United Kingdom", "The United Kingdom of Great Britain and Northern Ireland", "UN member state", "GB", "GBR", "826", ".uk", "London", "+00:00", "44", "GBP", "£", "British pound", "Penny", "100", "United Kingdom", "1:2 (2)", "67,081,234"});
        info.put("GD", new String[]{"Grenada", "Grenada", "UN member state", "GD", "GRD", "308", ".gd", "St. George''s", "-04:00", "1-473", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Grenada", "3:5 (1.667)", "113,000"});
        info.put("GE", new String[]{"Georgia", "Georgia", "UN member state", "GE", "GEO", "268", ".ge", "Tbilisi", "+04:00", "995", "GEL", "₾", "Georgian lari", "Tetri", "100", "Georgia", "2:3 (1.5)", "3,728,573"});
        info.put("GF", new String[]{"French Guiana", "Guyane", "France", "GF", "GUF", "254", ".gf", "Cayenne", "-03:00", "594", "EUR", "€", "Euro", "Cent", "100", "French Guiana", "2:3 (1.5)", "294,071"});
        info.put("GG", new String[]{"Guernsey", "The Bailiwick of Guernsey", "British Crown", "GG", "GGY", "831", ".gg", "Saint Peter Port", "+00:00", "44", "GBP", "£", "British pound", "Penny", "100", "Guernsey", "2:3 (1.5)", "63,124"});
        info.put("GH", new String[]{"Ghana", "The Republic of Ghana", "UN member state", "GH", "GHA", "288", ".gh", "Accra", "+00:00", "233", "GHS", "₵", "Ghanaian cedi", "Pesewa", "100", "Ghana", "2:3 (1.5)", "30,792,608"});
        info.put("GI", new String[]{"Gibraltar", "Gibraltar", "United Kingdom", "GI", "GIB", "292", ".gi", "Gibraltar", "+02:00", "350", "GIP", "£", "Gibraltar pound", "Penny", "100", "Gibraltar", "1:2 (2)", "34,000"});
        info.put("GL", new String[]{"Greenland", "Kalaallit Nunaat", "Denmark", "GL", "GRL", "304", ".gl", "Nuuk", "-03:00", "299", "DKK", "kr", "Danish krone", "Øre", "100", "Greenland", "2:3 (1.5)", "56,653"});
        info.put("GM", new String[]{"Gambia", "The Republic of The Gambia", "UN member state", "GM", "GMB", "270", ".gm", "Banjul", "+00:00", "220", "GMD", "D", "Gambian dalasi", "Butut", "100", "Gambia", "2:3 (1.5)", "2,487,000"});
        info.put("GN", new String[]{"Guinea", "The Republic of Guinea", "UN member state", "GN", "GIN", "324", ".gn", "Conakry", "+00:00", "224", "GNF", "Fr", "Guinean franc", "Centime", "100", "Guinea", "2:3 (1.5)", "12,907,395"});
        info.put("GP", new String[]{"Guadeloupe", "Guadeloupe", "France", "GP", "GLP", "312", ".gp", "Basse-Terre", "-04:00", "590", "EUR", "€", "Euro", "Cent", "100", "Guadeloupe", "2:3 (1.5)", "400,201"});
        info.put("GQ", new String[]{"Equatorial Guinea", "The Republic of Equatorial Guinea", "UN member state", "GQ", "GNQ", "226", ".gq", "Malabo", "+01:00", "240", "XAF", "Fr", "Central African CFA franc", "Centime", "100", "Equatorial Guinea", "2:3 (1.5)", "1,505,588"});
        info.put("GR", new String[]{"Greece", "The Hellenic Republic", "UN member state", "GR", "GRC", "300", ".gr", "Athens", "+02:00", "30", "EUR", "€", "Euro", "Cent", "100", "Greece", "2:3 (1.5)", "10,718,565"});
        info.put("GS", new String[]{"South Georgia and the South Sandwich Islands", "South Georgia and the South Sandwich Islands", "United Kingdom", "GS", "SGS", "239", ".gs", "King Edward Point", "-02:00", "500", "FKP", "£", "Falkland Islands pound", "Penny", "100", "South Georgia and the South Sandwich Islands", "1:2 (2)", "30"});
        info.put("GT", new String[]{"Guatemala", "The Republic of Guatemala", "UN member state", "GT", "GTM", "320", ".gt", "Guatemala City", "-06:00", "502", "GTQ", "Q", "Guatemalan quetzal", "Centavo", "100", "Guatemala", "5:8 (1.6)", "17,109,746"});
        info.put("GU", new String[]{"Guam", "The Territory of Guam", "United States", "GU", "GUM", "316", ".gu", "Hagåtña", "+10:00", "1-671", "USD", "$", "United States dollar", "Cent", "100", "Guam", "22:41 (1.864)", "178,306"});
        info.put("GW", new String[]{"Guinea-Bissau", "The Republic of Guinea-Bissau", "UN member state", "GW", "GNB", "624", ".gw", "Bissau", "+00:00", "245", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Guinea-Bissau", "1:2 (2)", "1,646,077"});
        info.put("GY", new String[]{"Guyana", "The Co-operative Republic of Guyana", "UN member state", "GY", "GUY", "328", ".gy", "Georgetown", "-04:00", "592", "GYD", "$", "Guyanese dollar", "Cent", "100", "Guyana", "3:5 (1.667)", "743,699"});
        info.put("HK", new String[]{"Hong Kong", "The Hong Kong Special Administrative Region of China", "China", "HK", "HKG", "344", ".hk", "Victoria City, Hong Kong", "+08:00", "852", "HKD", "$", "Hong Kong dollar", "Cent", "100", "Hong Kong", "2:3 (1.5)", "7,394,700"});
        info.put("HM", new String[]{"Heard Island and McDonald Islands", "The Territory of Heard Island and McDonald Islands", "Australia", "HM", "HMD", "334", ".hm", "", "+05:00", "672", "AUD", "$", "Australian dollar", "Cent", "100", "Australia", "1:2 (2)", "25,885,427"});
        info.put("HN", new String[]{"Honduras", "The Republic of Honduras", "UN member state", "HN", "HND", "340", ".hn", "Tegucigalpa", "-06:00", "504", "HNL", "L", "Honduran lempira", "Centavo", "100", "Honduras", "1:2 (2)", "9,450,711"});
        info.put("HR", new String[]{"Croatia", "The Republic of Croatia", "UN member state", "HR", "HRV", "191", ".hr", "Zagreb", "+01:00", "385", "HRK", "kn", "Croatian kuna", "Lipa", "100", "Croatia", "1:2 (2)", "4,036,355"});
        info.put("HT", new String[]{"Haiti", "The Republic of Haiti", "UN member state", "HT", "HTI", "332", ".ht", "Port-au-Prince", "-05:00", "509", "HTG", "G", "Haitian gourde", "Centime", "100", "Haiti", "3:5 (1.667)", "11,743,017"});
        info.put("HU", new String[]{"Hungary", "Hungary", "UN member state", "HU", "HUN", "348", ".hu", "Budapest", "+01:00", "36", "HUF", "Ft", "Hungarian forint", "Fillér", "100", "Hungary", "1:2 (2)", "9,730,772"});
        info.put("ID", new String[]{"Indonesia", "The Republic of Indonesia", "UN member state", "ID", "IDN", "360", ".id", "Jakarta", "+07:00", "62", "IDR", "Rp", "Indonesian rupiah", "Sen", "100", "Indonesia", "2:3 (1.5)", "271,350,000"});
        info.put("IE", new String[]{"Ireland", "Ireland", "UN member state", "IE", "IRL", "372", ".ie", "Dublin", "+00:00", "353", "EUR", "€", "Euro", "Cent", "100", "Ireland", "1:2 (2)", "5,011,500"});
        info.put("IL", new String[]{"Israel", "The State of Israel", "UN member state", "IL", "ISR", "376", ".il", "Jerusalem", "+02:00", "972", "ILS", "₪", "Israeli new shekel", "Agora", "100", "Israel", "8:11 (1.375)", "9,418,460"});
        info.put("IM", new String[]{"Isle of Man", "The Isle of Man", "British Crown", "IM", "IMN", "833", ".im", "Douglas", "+00:00", "44", "GBP", "£", "British pound", "Penny", "100", "Isle of Man", "1:2 (2)", "85,000"});
        info.put("IN", new String[]{"India", "The Republic of India", "UN member state", "IN", "IND", "356", ".in", "New Delhi", "+05:30", "91", "INR", "₹", "Indian rupee", "Paisa", "100", "India", "2:3 (1.5)", "1,383,404,120"});
        info.put("IO", new String[]{"British Indian Ocean Territory", "The British Indian Ocean Territory", "United Kingdom", "IO", "IOT", "086", ".io", "Camp Justice", "+06:00", "246", "USD", "$", "United States dollar", "Cent", "100", "British Indian Ocean Territory", "1:2 (2)", "400,201"});
        info.put("IQ", new String[]{"Iraq", "The Republic of Iraq", "UN member state", "IQ", "IRQ", "368", ".iq", "Baghdad", "+03:00", "964", "IQD", "ع.د", "Iraqi dinar", "Fils", "1000", "Iraq", "2:3 (1.5)", "41,190,700"});
        info.put("IR", new String[]{"Iran (Islamic Republic of)", "The Islamic Republic of Iran", "UN member state", "IR", "IRN", "364", ".ir", "Tehran", "+03:30", "98", "IRR", "﷼", "Iranian rial", "Rial", "1", "Iran", "4:7 (1.748)", "84,880,681"});
        info.put("IS", new String[]{"Iceland", "Iceland", "UN member state", "IS", "ISL", "352", ".is", "Reykjavik", "+00:00", "354", "ISK", "kr", "Icelandic króna", "Eyrir", "100", "Iceland", "18:25 (1.389)", "371,580"});
        info.put("IT", new String[]{"Italy", "The Italian Republic", "UN member state", "IT", "ITA", "380", ".it", "Rome", "+01:00", "39", "EUR", "€", "Euro", "Cent", "100", "Italy", "2:3 (1.5)", "59,108,671"});
        info.put("JE", new String[]{"Jersey", "The Bailiwick of Jersey", "British Crown", "JE", "JEY", "832", ".je", "Saint Helier", "+00:00", "44", "GBP", "£", "British pound", "Penny", "100", "Jersey", "3:5 (1.667)", "107,800"});
        info.put("JM", new String[]{"Jamaica", "Jamaica", "UN member state", "JM", "JAM", "388", ".jm", "Kingston", "-05:00", "1-876", "JMD", "$", "Jamaican dollar", "Cent", "100", "Jamaica", "1:2 (2)", "2,734,093"});
        info.put("JO", new String[]{"Jordan", "The Hashemite Kingdom of Jordan", "UN member state", "JO", "JOR", "400", ".jo", "Amman", "+02:00", "962", "JOD", "د.ا", "Jordanian dinar", "Piastre", "100", "Jordan", "1:2 (2)", "11,060,628"});
        info.put("JP", new String[]{"Japan", "Japan", "UN member state", "JP", "JPN", "392", ".jp", "Tokyo", "+09:00", "81", "JPY", "¥ or 円", "Japanese yen", "Sen", "100", "Japan", "2:3 (1.5)", "125,120,000"});
        info.put("KE", new String[]{"Kenya", "The Republic of Kenya", "UN member state", "KE", "KEN", "404", ".ke", "Nairobi", "+03:00", "254", "KES", "Sh", "Kenyan shilling", "Cent", "100", "Kenya", "2:3 (1.5)", "47,564,296"});
        info.put("KG", new String[]{"Kyrgyzstan", "The Kyrgyz Republic", "UN member state", "KG", "KGZ", "417", ".kg", "Bishkek", "+06:00", "996", "KGS", "с", "Kyrgyzstani som", "Tyiyn", "100", "Kyrgyzstan", "3:5 (1.667)", "6,663,000"});
        info.put("KH", new String[]{"Cambodia", "The Kingdom of Cambodia", "UN member state", "KH", "KHM", "116", ".kh", "Phnom Penh", "+07:00", "855", "KHR", "៛", "Cambodian riel", "Sen", "100", "Cambodia", "16:25 (1.563)", "15,552,211"});
        info.put("KI", new String[]{"Kiribati", "The Republic of Kiribati", "UN member state", "KI", "KIR", "296", ".ki", "South Tarawa", "+12:00", "686", "AUD", "$", "Australian dollar", "Cent", "100", "Kiribati", "1:2 (2)", "120,740"});
        info.put("KM", new String[]{"Comoros", "The Union of the Comoros", "UN member state", "KM", "COM", "174", ".km", "Moroni", "+03:00", "269", "KMF", "Fr", "Comorian franc", "Centime", "100", "Comoros", "3:5 (1.667)", "758,316"});
        info.put("KN", new String[]{"Saint Kitts and Nevis", "Saint Kitts and Nevis", "UN member state", "KN", "KNA", "659", ".kn", "Basseterre", "-04:00", "1-869", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Saint Kitts and Nevis", "2:3 (1.5)", "54,000"});
        info.put("KP", new String[]{"North Korea", "The Democratic People's Republic of Korea", "UN member state", "KP", "PRK", "408", ".kp", "Pyongyang", "+09:00", "850", "KPW", "₩", "North Korean won", "Chon", "100", "North Korea", "1:2 (2)", "25,660,000"});
        info.put("KR", new String[]{"South Korea", "The Republic of Korea", "UN member state", "KR", "KOR", "410", ".kr", "Seoul", "+09:00", "82", "KRW", "₩", "South Korean won", "Jeon", "100", "South Korea", "2:3 (1.5)", "51,671,569"});
        info.put("KW", new String[]{"Kuwait", "The State of Kuwait", "UN member state", "KW", "KWT", "414", ".kw", "Kuwait City", "+03:00", "965", "KWD", "د.ك", "Kuwaiti dinar", "Fils", "1000", "Kuwait", "1:2 (2)", "4,670,713"});
        info.put("KY", new String[]{"Cayman Islands", "The Cayman Islands", "United Kingdom", "KY", "CYM", "136", ".ky", "George Town", "-05:00", "1-345", "KYD", "$", "Cayman Islands dollar", "Cent", "100", "Cayman Islands", "1:2 (2)", "65,786"});
        info.put("KZ", new String[]{"Kazakhstan", "The Republic of Kazakhstan", "UN member state", "KZ", "KAZ", "398", ".kz", "Nur-Sultan", "+06:00", "7", "KZT", "₸", "Kazakhstani tenge", "Tıyn", "100", "Kazakhstan", "1:2 (2)", "19,024,360"});
        info.put("LA", new String[]{"Lao People's Democratic Republic", "The Lao People's Democratic Republic", "UN member state", "LA", "LAO", "418", ".la", "Vientiane", "+07:00", "856", "LAK", "₭", "Lao kip", "Att", "100", "Laos", "2:3 (1.5)", "7,337,783"});
        info.put("LB", new String[]{"Lebanon", "The Lebanese Republic", "UN member state", "LB", "LBN", "422", ".lb", "Beirut", "+02:00", "961", "LBP", "ل.ل", "Lebanese pound", "Piastre", "100", "Lebanon", "2:3 (1.5)", "6,769,000"});
        info.put("LC", new String[]{"Saint Lucia", "Saint Lucia", "UN member state", "LC", "LCA", "662", ".lc", "Castries", "-04:00", "1-758", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Saint Lucia", "1:2 (2)", "178,696"});
        info.put("LI", new String[]{"Liechtenstein", "The Principality of Liechtenstein", "UN member state", "LI", "LIE", "438", ".li", "Vaduz", "+01:00", "423", "CHF", "Fr.", "Swiss franc", "Centime", "100", "Liechtenstein", "3:5 (1.667)", "39,062"});
        info.put("LK", new String[]{"Sri Lanka", "The Democratic Socialist Republic of Sri Lanka", "UN member state", "LK", "LKA", "144", ".lk", "Colombo", "+05:30", "94", "LKR", "Rs රු or ரூ", "Sri Lankan rupee", "Cent", "100", "Sri Lanka", "1:2 (2)", "22,156,000"});
        info.put("LR", new String[]{"Liberia", "The Republic of Liberia", "UN member state", "LR", "LBR", "430", ".lr", "Monrovia", "+00:00", "231", "LRD", "$", "Liberian dollar", "Cent", "100", "Liberia", "10:19 (1.9)", "4,661,010"});
        info.put("LS", new String[]{"Lesotho", "The Kingdom of Lesotho", "UN member state", "LS", "LSO", "426", ".ls", "Maseru", "+02:00", "266", "LSL", "L", "Lesotho loti", "Sente", "100", "Lesotho", "2:3 (1.5)", "2,159,000"});
        info.put("LT", new String[]{"Lithuania", "The Republic of Lithuania", "UN member state", "LT", "LTU", "440", ".lt", "Vilnius", "+02:00", "370", "EUR", "€", "Euro", "Cent", "100", "Lithuania", "3:5 (1.667)", "2,780,427"});
        info.put("LU", new String[]{"Luxembourg", "The Grand Duchy of Luxembourg", "UN member state", "LU", "LUX", "442", ".lu", "Luxembourg", "+01:00", "352", "EUR", "€", "Euro", "Cent", "100", "Luxembourg", "3:5 (1.667)", "634,730"});
        info.put("LV", new String[]{"Latvia", "The Republic of Latvia", "UN member state", "LV", "LVA", "428", ".lv", "Riga", "+02:00", "371", "EUR", "€", "Euro", "Cent", "100", "Latvia", "1:2 (2)", "1,880,700"});
        info.put("LY", new String[]{"Libya", "The State of Libya", "UN member state", "LY", "LBY", "434", ".ly", "Tripoli", "+02:00", "218", "LYD", "ل.د", "Libyan dinar", "Dirham", "1000", "Libya", "1:2 (2)", "6,959,000"});
        info.put("MA", new String[]{"Morocco", "The Kingdom of Morocco", "UN member state", "MA", "MAR", "504", ".ma", "Rabat", "+00:00", "212", "MAD", "د. م.", "Moroccan dirham", "Centime", "100", "Morocco", "2:3 (1.5)", "36,404,425"});
        info.put("MC", new String[]{"Monaco", "The Principality of Monaco", "UN member state", "MC", "MCO", "492", ".mc", "Monaco", "+01:00", "377", "EUR", "€", "Euro", "Cent", "100", "Monaco", "4:5 (1.25)", "38,350"});
        info.put("MD", new String[]{"Moldova (the Republic of)", "The Republic of Moldova", "UN member state", "MD", "MDA", "498", ".md", "Chișinău", "+02:00", "373", "MDL", "L", "Moldovan leu", "Ban", "100", "Moldova", "1:2 (2)", "2,597,100"});
        info.put("ME", new String[]{"Montenegro", "Montenegro", "UN member state", "ME", "MNE", "499", ".me", "Podgorica", "+01:00", "382", "EUR", "€", "Euro", "Cent", "100", "Montenegro", "1:2 (2)", "621,306"});
        info.put("MF", new String[]{"Saint Martin (French part)", "The Collectivity of Saint-Martin", "France", "MF", "MAF", "663", ".mf", "Marigot", "-04:00", "590", "EUR", "€", "Euro", "Cent", "100", "France", "2:3 (1.5)", "67,486,000"});
        info.put("MG", new String[]{"Madagascar", "The Republic of Madagascar", "UN member state", "MG", "MDG", "450", ".mg", "Antananarivo", "+03:00", "261", "MGA", "Ar", "Malagasy ariary", "Iraimbilanja", "5", "Madagascar", "2:3 (1.5)", "26,923,353"});
        info.put("MH", new String[]{"Marshall Islands", "The Republic of the Marshall Islands", "UN member state", "MH", "MHL", "584", ".mh", "Majuro", "+12:00", "692", "USD", "$", "United States dollar", "Cent", "100", "Marshall Islands", "10:19 (1.9)", "54,516"});
        info.put("MK", new String[]{"North Macedonia", "The Republic of North Macedonia", "UN member state", "MK", "MKD", "807", ".mk", "Skopje", "+01:00", "389", "MKD", "ден", "Macedonian denar", "Deni", "100", "North Macedonia", "1:2 (2)", "2,068,808"});
        info.put("ML", new String[]{"Mali", "The Republic of Mali", "UN member state", "ML", "MLI", "466", ".ml", "Bamako", "+00:00", "223", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Mali", "2:3 (1.5)", "20,856,000"});
        info.put("MM", new String[]{"Myanmar", "The Republic of the Union of Myanmar", "UN member state", "MM", "MMR", "104", ".mm", "Naypyidaw", "+06:30", "95", "MMK", "Ks", "Burmese kyat", "Pya", "100", "Myanmar", "2:3 (1.5)", "55,294,979"});
        info.put("MN", new String[]{"Mongolia", "Mongolia", "UN member state", "MN", "MNG", "496", ".mn", "Ulaanbaatar", "+08:00", "976", "MNT", "₮", "Mongolian tögrög", "Möngö", "100", "Mongolia", "1:2 (2)", "3,403,686"});
        info.put("MO", new String[]{"Macao", "The Macao Special Administrative Region of China", "China", "MO", "MAC", "446", ".mo", "Macau", "+08:00", "853", "MOP", "MOP$", "Macanese pataca", "Avo", "100", "Macau", "2:3 (1.5)", "682,500"});
        info.put("MP", new String[]{"Northern Mariana Islands", "The Commonwealth of the Northern Mariana Islands", "United States", "MP", "MNP", "580", ".mp", "Saipan", "+10:00", "1-670", "USD", "$", "United States dollar", "Cent", "100", "Northern Mariana Islands", "1:2 (2)", "56,801"});
        info.put("MQ", new String[]{"Martinique", "Martinique", "France", "MQ", "MTQ", "474", ".mq", "Fort-de-France", "-04:00", "596", "EUR", "€", "Euro", "Cent", "100", "Martinique", "2:3 (1.5)", "375,053"});
        info.put("MR", new String[]{"Mauritania", "The Islamic Republic of Mauritania", "UN member state", "MR", "MRT", "478", ".mr", "Nouakchott", "+00:00", "222", "MRU", "UM", "Mauritanian ouguiya", "Khoums", "5", "Mauritania", "2:3 (1.5)", "4,271,197"});
        info.put("MS", new String[]{"Montserrat", "Montserrat", "United Kingdom", "MS", "MSR", "500", ".ms", "Plymouth", "-04:00", "1-664", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Montserrat", "1:2 (2)", "5,000"});
        info.put("MT", new String[]{"Malta", "The Republic of Malta", "UN member state", "MT", "MLT", "470", ".mt", "Valletta", "+01:00", "356", "EUR", "€", "Euro", "Cent", "100", "Malta", "2:3 (1.5)", "514,564"});
        info.put("MU", new String[]{"Mauritius", "The Republic of Mauritius", "UN member state", "MU", "MUS", "480", ".mu", "Port Louis", "+04:00", "230", "MUR", "₨", "Mauritian rupee", "Cent", "100", "Mauritius", "2:3 (1.5)", "1,266,334"});
        info.put("MV", new String[]{"Maldives", "The Republic of Maldives", "UN member state", "MV", "MDV", "462", ".mv", "Malé", "+05:00", "960", "MVR", ".ރ", "Maldivian rufiyaa", "Laari", "100", "Maldives", "2:3 (1.5)", "383,135"});
        info.put("MW", new String[]{"Malawi", "The Republic of Malawi", "UN member state", "MW", "MWI", "454", ".mw", "Lilongwe", "+02:00", "265", "MWK", "MK", "Malawian kwacha", "Tambala", "100", "Malawi", "2:3 (1.5)", "18,898,441"});
        info.put("MX", new String[]{"Mexico", "The United Mexican States", "UN member state", "MX", "MEX", "484", ".mx", "Mexico City", "-06:00", "52", "MXN", "$", "Mexican peso", "Centavo", "100", "Mexico", "4:7 (1.75)", "126,014,024"});
        info.put("MY", new String[]{"Malaysia", "Malaysia", "UN member state", "MY", "MYS", "458", ".my", "Kuala Lumpur", "+08:00", "60", "MYR", "RM", "Malaysian ringgit", "Sen", "100", "Malaysia", "1:2 (2)", "32,722,800"});
        info.put("MZ", new String[]{"Mozambique", "The Republic of Mozambique", "UN member state", "MZ", "MOZ", "508", ".mz", "Maputo", "+02:00", "258", "MZN", "MT", "Mozambican metical", "Centavo", "100", "Mozambique", "2:3 (1.5)", "30,832,244"});
        info.put("NA", new String[]{"Namibia", "The Republic of Namibia", "UN member state", "NA", "NAM", "516", ".na", "Windhoek", "+02:00", "264", "NAD", "$", "Namibian dollar", "Cent", "100", "Namibia", "2:3 (1.5)", "2,550,226"});
        info.put("NC", new String[]{"New Caledonia", "New Caledonia", "France", "NC", "NCL", "540", ".nc", "Nouméa", "+11:00", "687", "XPF", "₣", "CFP franc", "Centime", "100", "New Caledonia", "1:2 (2)", "273,674"});
        info.put("NE", new String[]{"Niger", "The Republic of the Niger", "UN member state", "NE", "NER", "562", ".ne", "Niamey", "+01:00", "227", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Niger", "6:7 (1.167)", "24,112,753"});
        info.put("NF", new String[]{"Norfolk Island", "The Territory of Norfolk Island", "Australia", "NF", "NFK", "574", ".nf", "Kingston", "+11:00", "672", "AUD", "$", "Australian dollar", "Cent", "100", "Norfolk Island", "1:2 (2)", "1,734"});
        info.put("NG", new String[]{"Nigeria", "The Federal Republic of Nigeria", "UN member state", "NG", "NGA", "566", ".ng", "Abuja", "+01:00", "234", "NGN", "₦", "Nigerian naira", "Kobo", "100", "Nigeria", "1:2 (2)", "211,401,000"});
        info.put("NI", new String[]{"Nicaragua", "The Republic of Nicaragua", "UN member state", "NI", "NIC", "558", ".ni", "Managua", "-06:00", "505", "NIO", "C$", "Nicaraguan córdoba", "Centavo", "100", "Nicaragua", "3:5 (1.667)", "6,595,674"});
        info.put("NL", new String[]{"Netherlands", "The Kingdom of the Netherlands", "UN member state", "NL", "NLD", "528", ".nl", "Amsterdam", "+01:00", "31", "EUR", "€", "Euro", "Cent", "100", "Netherlands", "2:3 (1.5)", "17,649,831"});
        info.put("NO", new String[]{"Norway", "The Kingdom of Norway", "UN member state", "NO", "NOR", "578", ".no", "Oslo", "+01:00", "47", "NOK", "kr", "Norwegian krone", "Øre", "100", "Norway", "8:11 (1.375)", "5,402,171"});
        info.put("NP", new String[]{"Nepal", "The Federal Democratic Republic of Nepal", "UN member state", "NP", "NPL", "524", ".np", "Kathmandu", "+05:45", "977", "NPR", "रू", "Nepalese rupee", "Paisa", "100", "Nepal", "≈50:41 (0.820)", "30,378,055"});
        info.put("NR", new String[]{"Nauru", "The Republic of Nauru", "UN member state", "NR", "NRU", "520", ".nr", "Yaren", "+12:00", "674", "AUD", "$", "Australian dollar", "Cent", "100", "Nauru", "1:2 (2)", "11,832"});
        info.put("NU", new String[]{"Niue", "Niue", "New Zealand", "NU", "NIU", "570", ".nu", "Alofi", "-11:00", "683", "NZD", "$", "New Zealand dollar", "Cent", "100", "Niue", "1:2 (2)", "1,549"});
        info.put("NZ", new String[]{"New Zealand", "New Zealand", "UN member state", "NZ", "NZL", "554", ".nz", "Wellington", "+12:00", "64", "NZD", "$", "New Zealand dollar", "Cent", "100", "New Zealand", "1:2 (2)", "5,132,871"});
        info.put("OM", new String[]{"Oman", "The Sultanate of Oman", "UN member state", "OM", "OMN", "512", ".om", "Muscat", "+04:00", "968", "OMR", "ر.ع.", "Omani rial", "Baisa", "1000", "Oman", "1:2 (2)", "4,513,666"});
        info.put("PA", new String[]{"Panama", "The Republic of Panamá", "UN member state", "PA", "PAN", "591", ".pa", "Panama City", "-05:00", "507", "PAB", "B/.", "Panamanian balboa", "Centésimo", "100", "Panama", "2:3 (1.5)", "4,278,500"});
        info.put("PE", new String[]{"Peru", "The Republic of Perú", "UN member state", "PE", "PER", "604", ".pe", "Lima", "-05:00", "51", "PEN", "S/.", "Peruvian sol", "Céntimo", "100", "Peru", "2:3 (1.5)", "33,035,304"});
        info.put("PF", new String[]{"French Polynesia", "French Polynesia", "France", "PF", "PYF", "258", ".pf", "Papeete", "-10:00", "689", "XPF", "₣", "CFP franc", "Centime", "100", "French Polynesia", "2:3 (1.5)", "279,890"});
        info.put("PG", new String[]{"Papua New Guinea", "The Independent State of Papua New Guinea", "UN member state", "PG", "PNG", "598", ".pg", "Port Moresby", "+10:00", "675", "PGK", "K", "Papua New Guinean kina", "Toea", "100", "Papua New Guinea", "3:4 (1.333)", "9,122,994"});
        info.put("PH", new String[]{"Philippines", "The Republic of the Philippines", "UN member state", "PH", "PHL", "608", ".ph", "Manila", "+08:00", "63", "PHP", "₱", "Philippine peso", "Sentimo", "100", "Philippines", "1:2 (2)", "110,989,707"});
        info.put("PK", new String[]{"Pakistan", "The Islamic Republic of Pakistan", "UN member state", "PK", "PAK", "586", ".pk", "Islamabad", "+05:00", "92", "PKR", "₨", "Pakistani rupee", "Paisa", "100", "Pakistan", "2:3 (1.5)", "225,200,000"});
        info.put("PL", new String[]{"Poland", "The Republic of Poland", "UN member state", "PL", "POL", "616", ".pl", "Warsaw", "+01:00", "48", "PLN", "zł", "Polish złoty", "Grosz", "100", "Poland", "5:8 (1.6)", "38,158,000"});
        info.put("PM", new String[]{"Saint Pierre and Miquelon", "The Overseas Collectivity of Saint-Pierre and Miquelon", "France", "PM", "SPM", "666", ".pm", "Saint-Pierre", "-03:00", "508", "EUR", "€", "Euro", "Cent", "100", "Saint-Pierre and Miquelon", "2:3 (1.5)", "5,985"});
        info.put("PN", new String[]{"Pitcairn", "The Pitcairn, Henderson, Ducie and Oeno Islands", "United Kingdom", "PN", "PCN", "612", ".pn", "Adamstown", "-08:00", "872", "NZD", "$", "New Zealand dollar", "Cent", "100", "Pitcairn Islands", "1:2 (2)", "40"});
        info.put("PR", new String[]{"Puerto Rico", "The Commonwealth of Puerto Rico", "United States", "PR", "PRI", "630", ".pr", "San Juan", "-04:00", "1-787", "USD", "$", "United States dollar", "Cent", "100", "Puerto Rico", "2:3 (1.5)", "3,285,874"});
        info.put("PS", new String[]{"Palestine, State of", "The State of Palestine", "UN observer state", "PS", "PSE", "275", ".ps", "Jerusalem", "+02:00", "970", "ILS", "₪", "Israeli new shekel", "Agora", "100", "Palestine", "1:2 (2)", "5,227,193"});
        info.put("PT", new String[]{"Portugal", "The Portuguese Republic", "UN member state", "PT", "PRT", "620", ".pt", "Lisbon", "+00:00", "351", "EUR", "€", "Euro", "Cent", "100", "Portugal", "2:3 (1.5)", "10,347,892"});
        info.put("PW", new String[]{"Palau", "The Republic of Palau", "UN member state", "PW", "PLW", "585", ".pw", "Ngerulmud", "+09:00", "680", "USD", "$", "United States dollar", "Cent", "100", "Palau", "5:8 (1.6)", "17,957"});
        info.put("PY", new String[]{"Paraguay", "The Republic of Paraguay", "UN member state", "PY", "PRY", "600", ".py", "Asunción", "-03:00", "595", "PYG", "₲", "Paraguayan guaraní", "Céntimo", "100", "Paraguay", "11:20 (1.818)", "7,353,038"});
        info.put("QA", new String[]{"Qatar", "The State of Qatar", "UN member state", "QA", "QAT", "634", ".qa", "Doha", "+03:00", "974", "QAR", "ر.ق", "Qatari riyal", "Dirham", "100", "Qatar", "11:28 (2.545)", "2,799,202"});
        info.put("RE", new String[]{"Réunion", "Réunion", "France", "RE", "REU", "638", ".re", "Saint-Denis", "+04:00", "262", "EUR", "€", "Euro", "Cent", "100", "Reunion", "2:3 (1.5)", "858,450"});
        info.put("RO", new String[]{"Romania", "Romania", "UN member state", "RO", "ROU", "642", ".ro", "Bucharest", "+02:00", "40", "RON", "lei", "Romanian leu", "Ban", "100", "Romania", "2:3 (1.5)", "19,186,201"});
        info.put("RS", new String[]{"Serbia", "The Republic of Serbia", "UN member state", "RS", "SRB", "688", ".rs", "Belgrade", "+01:00", "381", "RSD", "дин. or din.", "Serbian dinar", "Para", "100", "Serbia", "2:3 (1.5)", "6,871,547"});
        info.put("RU", new String[]{"Russian Federation", "The Russian Federation", "UN member state", "RU", "RUS", "643", ".ru", "Moscow", "+03:00", "7", "RUB", "₽", "Russian ruble", "Kopek", "100", "Russia", "2:3 (1.5)", "146,171,015"});
        info.put("RW", new String[]{"Rwanda", "The Republic of Rwanda", "UN member state", "RW", "RWA", "646", ".rw", "Kigali", "+02:00", "250", "RWF", "Fr", "Rwandan franc", "Centime", "100", "Rwanda", "2:3 (1.5)", "12,955,768"});
        info.put("SA", new String[]{"Saudi Arabia", "The Kingdom of Saudi Arabia", "UN member state", "SA", "SAU", "682", ".sa", "Riyadh", "+03:00", "966", "SAR", "﷼", "Saudi riyal", "Halala", "100", "Saudi Arabia", "2:3 (1.5)", "35,013,414"});
        info.put("SB", new String[]{"Solomon Islands", "The Solomon Islands", "UN member state", "SB", "SLB", "090", ".sb", "Honiara", "+11:00", "677", "SBD", "$", "Solomon Islands dollar", "Cent", "100", "Solomon Islands", "1:2 (2)", "728,041"});
        info.put("SC", new String[]{"Seychelles", "The Republic of Seychelles", "UN member state", "SC", "SYC", "690", ".sc", "Victoria", "+04:00", "248", "SCR", "₨", "Seychellois rupee", "Cent", "100", "Seychelles", "1:2 (2)", "99,202"});
        info.put("SD", new String[]{"Sudan", "The Republic of the Sudan", "UN member state", "SD", "SDN", "729", ".sd", "Khartoum", "+02:00", "249", "SDG", "ج.س.", "Sudanese pound", "Piastre", "100", "Sudan", "1:2 (2)", "43,889,820"});
        info.put("SE", new String[]{"Sweden", "The Kingdom of Sweden", "UN member state", "SE", "SWE", "752", ".se", "Stockholm", "+01:00", "46", "SEK", "kr", "Swedish krona", "Öre", "100", "Sweden", "5:8 (1.6)", "10,427,296"});
        info.put("SG", new String[]{"Singapore", "The Republic of Singapore", "UN member state", "SG", "SGP", "702", ".sg", "Singapore", "+08:00", "65", "SGD", "$", "Singapore dollar", "Cent", "100", "Singapore", "2:3 (1.5)", "5,453,600"});
        info.put("SH", new String[]{"Tristan da Cunha", "Saint Helena, Ascension and Tristan da Cunha", "United Kingdom", "SH", "SHN", "654", ".sh", "Jamestown", "+00:00", "290", "SHP", "£", "Saint Helena pound", "Penny", "100", "United Kingdom", "1:2 (2)", "67,081,234"});
        info.put("SI", new String[]{"Slovenia", "The Republic of Slovenia", "UN member state", "SI", "SVN", "705", ".si", "Ljubljana", "+01:00", "386", "EUR", "€", "Euro", "Cent", "100", "Slovenia", "1:2 (2)", "2,108,977"});
        info.put("SJ", new String[]{"Jan Mayen", "Svalbard and Jan Mayen", "Norway", "SJ", "SJM", "744", "(.sj)", "Svalbard and Jan Mayen", "+01:00", "47", "NOK", "kr", "Norwegian krone", "Øre", "100", "Norway", "8:11 (1.375)", "5,402,171"});
        info.put("SK", new String[]{"Slovakia", "The Slovak Republic", "UN member state", "SK", "SVK", "703", ".sk", "Bratislava", "+01:00", "421", "EUR", "€", "Euro", "Cent", "100", "Slovakia", "2:3 (1.5)", "5,459,781"});
        info.put("SL", new String[]{"Sierra Leone", "The Republic of Sierra Leone", "UN member state", "SL", "SLE", "694", ".sl", "Freetown", "+00:00", "232", "SLL", "Le", "Sierra Leonean leone", "Cent", "100", "Sierra Leone", "2:3 (1.5)", "8,297,882"});
        info.put("SM", new String[]{"San Marino", "The Republic of San Marino", "UN member state", "SM", "SMR", "674", ".sm", "San Marino", "+01:00", "378", "EUR", "€", "Euro", "Cent", "100", "San Marino", "3:4 (1.333)", "33,641"});
        info.put("SN", new String[]{"Senegal", "The Republic of Senegal", "UN member state", "SN", "SEN", "686", ".sn", "Dakar", "+00:00", "221", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Senegal", "2:3 (1.5)", "17,223,497"});
        info.put("SO", new String[]{"Somalia", "The Federal Republic of Somalia", "UN member state", "SO", "SOM", "706", ".so", "Mogadishu", "+03:00", "252", "SOS", "Sh", "Somali shilling", "Cent", "100", "Somalia", "2:3 (1.5)", "16,360,000"});
        info.put("SR", new String[]{"Suriname", "The Republic of Suriname", "UN member state", "SR", "SUR", "740", ".sr", "Paramaribo", "-03:00", "597", "SRD", "$", "Surinamese dollar", "Cent", "100", "Suriname", "2:3 (1.5)", "598,000"});
        info.put("SS", new String[]{"South Sudan", "The Republic of South Sudan", "UN member state", "SS", "SSD", "728", ".ss", "Juba", "+02:00", "211", "SSP", "£", "South Sudanese pound", "Piaster", "100", "South Sudan", "1:2 (2)", "13,249,924"});
        info.put("ST", new String[]{"Sao Tome and Principe", "The Democratic Republic of São Tomé and Príncipe", "UN member state", "ST", "STP", "678", ".st", "São Tomé", "+00:00", "239", "STN", "Db", "São Tomé and Príncipe dobra", "Cêntimo", "100", "São Tomé and Príncipe", "1:2 (2)", "214,610"});
        info.put("SV", new String[]{"El Salvador", "The Republic of El Salvador", "UN member state", "SV", "SLV", "222", ".sv", "San Salvador", "-06:00", "503", "USD", "$", "United States dollar", "Cent", "100", "El Salvador", "189:335 (1.772)", "6,825,935"});
        info.put("SX", new String[]{"Sint Maarten (Dutch part)", "Sint Maarten", "Netherlands", "SX", "SXM", "534", ".sx", "Philipsburg", "-04:00", "1-721", "ANG", "ƒ", "Netherlands Antillean guilder", "Cent", "100", "Sint Maarten", "2:3 (1.5)", "41,486"});
        info.put("SY", new String[]{"Syrian Arab Republic", "The Syrian Arab Republic", "UN member state", "SY", "SYR", "760", ".sy", "Damascus", "+02:00", "963", "SYP", "£ or ل.س", "Syrian pound", "Piastre", "100", "Syria", "2:3 (1.5)", "18,276,000"});
        info.put("SZ", new String[]{"Eswatini", "The Kingdom of Eswatini", "UN member state", "SZ", "SWZ", "748", ".sz", "Mbabane", "+02:00", "268", "SZL", "L", "Swazi lilangeni", "Cent", "100", "Eswatini", "2:3 (1.5)", "1,172,000"});
        info.put("TC", new String[]{"Turks and Caicos Islands", "The Turks and Caicos Islands", "United Kingdom", "TC", "TCA", "796", ".tc", "Cockburn Town", "-05:00", "1-649", "USD", "$", "United States dollar", "Cent", "100", "Turks and Caicos Islands", "1:2 (2)", "42,953"});
        info.put("TD", new String[]{"Chad", "The Republic of Chad", "UN member state", "TD", "TCD", "148", ".td", "N'Djamena", "+01:00", "235", "XAF", "Fr", "Central African CFA franc", "Centime", "100", "Chad", "2:3 (1.5)", "16,818,391"});
        info.put("TF", new String[]{"French Southern Territories", "The French Southern and Antarctic Lands", "France", "TF", "ATF", "260", ".tf", "", "+05:00", "262", "EUR", "€", "Euro", "Cent", "100", "French Southern and Antarctic Lands", "2:3 (1.5)", "300"});
        info.put("TG", new String[]{"Togo", "The Togolese Republic", "UN member state", "TG", "TGO", "768", ".tg", "Lomé", "+00:00", "228", "XOF", "Fr", "West African CFA franc", "Centime", "100", "Togo", "2:3.23607 (1.618034)", "7,886,000"});
        info.put("TH", new String[]{"Thailand", "The Kingdom of Thailand", "UN member state", "TH", "THA", "764", ".th", "Bangkok", "+07:00", "66", "THB", "฿", "Thai baht", "Satang", "100", "Thailand", "2:3 (1.5)", "66,708,705"});
        info.put("TJ", new String[]{"Tajikistan", "The Republic of Tajikistan", "UN member state", "TJ", "TJK", "762", ".tj", "Dushanbe", "+05:00", "992", "TJS", "с.", "Tajikistani somoni", "Diram", "100", "Tajikistan", "1:2 (2)", "9,504,000"});
        info.put("TK", new String[]{"Tokelau", "Tokelau", "New Zealand", "TK", "TKL", "772", ".tk", "Atafu,Tokelau", "+13:00", "690", "NZD", "$", "New Zealand dollar", "Cent", "100", "Tokelau", "1:2 (2)", "1,501"});
        info.put("TL", new String[]{"Timor-Leste", "The Democratic Republic of Timor-Leste", "UN member state", "TL", "TLS", "626", ".tl", "Dili", "+09:00", "670", "USD", "$", "United States dollar", "Cent", "100", "Timor-Leste", "1:2 (2)", "1,317,780"});
        info.put("TM", new String[]{"Turkmenistan", "Turkmenistan", "UN member state", "TM", "TKM", "795", ".tm", "Ashgabat", "+05:00", "993", "TMT", "m.", "Turkmenistan manat", "Tennesi", "100", "Turkmenistan", "2:3 (1.5)", "6,118,000"});
        info.put("TN", new String[]{"Tunisia", "The Republic of Tunisia", "UN member state", "TN", "TUN", "788", ".tn", "Tunis", "+01:00", "216", "TND", "د.ت", "Tunisian dinar", "Millime", "1000", "Tunisia", "2:3 (1.5)", "11,746,695"});
        info.put("TO", new String[]{"Tonga", "The Kingdom of Tonga", "UN member state", "TO", "TON", "776", ".to", "Nuku'alofa", "+13:00", "676", "TOP", "T$", "Tongan paʻanga", "Seniti", "100", "Tonga", "1:2 (2)", "99,532"});
        info.put("TR", new String[]{"Turkey", "The Republic of Turkey", "UN member state", "TR", "TUR", "792", ".tr", "Ankara", "+03:00", "90", "TRY", "₺", "Turkish lira", "Kuruş", "100", "Turkey", "2:3 (1.5)", "83,614,362"});
        info.put("TT", new String[]{"Trinidad and Tobago", "The Republic of Trinidad and Tobago", "UN member state", "TT", "TTO", "780", ".tt", "Port of Spain", "-04:00", "1-868", "TTD", "$", "Trinidad and Tobago dollar", "Cent", "100", "Trinidad and Tobago", "3:5 (1.667)", "1,367,558"});
        info.put("TV", new String[]{"Tuvalu", "Tuvalu", "UN member state", "TV", "TUV", "798", ".tv", "Funafuti", "+12:00", "688", "AUD", "$", "Australian dollar", "Cent", "100", "Tuvalu", "1:2 (2)", "10,679"});
        info.put("TW", new String[]{"Taiwan (Province of China)", "The Republic of China", "Disputed", "TW", "TWN", "158", ".tw", "Taipei", "+08:00", "886", "TWD", "$", "New Taiwan dollar", "Cent", "100", "Taiwan", "2:3 (1.5)", "23,430,948"});
        info.put("TZ", new String[]{"Tanzania, the United Republic of", "The United Republic of Tanzania", "UN member state", "TZ", "TZA", "834", ".tz", "Dodoma", "+03:00", "255", "TZS", "Sh", "Tanzanian shilling", "Cent", "100", "Tanzania", "2:3 (1.5)", "59,441,988"});
        info.put("UA", new String[]{"Ukraine", "Ukraine", "UN member state", "UA", "UKR", "804", ".ua", "Kyiv", "+02:00", "380", "UAH", "₴", "Ukrainian hryvnia", "Kopiyka", "100", "Ukraine", "2:3 (1.5)", "41,342,465"});
        info.put("UG", new String[]{"Uganda", "The Republic of Uganda", "UN member state", "UG", "UGA", "800", ".ug", "Kampala", "+03:00", "256", "UGX", "Sh", "Ugandan shilling", "(none)", "(none)", "Uganda", "2:3 (1.5)", "42,885,900"});
        info.put("UM", new String[]{"United States Minor Outlying Islands", "Wake Island", "United States", "UM", "UMI", "581", "(.um)", "", "-11:00", "246", "USD", "$", "United States dollar", "Cent", "100", "United States Minor Outlying Islands", "10:19 (1.9)", "300"});
        info.put("US", new String[]{"United States of America", "The United States of America", "UN member state", "US", "USA", "840", ".us", "Washington", "-04:00", "1", "USD", "$", "United States dollar", "Cent", "100", "United States", "10:19 (1.9)", "332,576,473"});
        info.put("UY", new String[]{"Uruguay", "The Oriental Republic of Uruguay", "UN member state", "UY", "URY", "858", ".uy", "Montevideo", "-03:00", "598", "UYU", "$", "Uruguayan peso", "Centésimo", "100", "Uruguay", "2:3 (1.5)", "3,554,915"});
        info.put("UZ", new String[]{"Uzbekistan", "The Republic of Uzbekistan", "UN member state", "UZ", "UZB", "860", ".uz", "Tashkent", "+05:00", "998", "UZS", "Sʻ", "Uzbekistani soʻm", "Tiyin", "100", "Uzbekistan", "1:2 (2)", "35,159,708"});
        info.put("VA", new String[]{"Holy See", "The Holy See", "UN observer state", "VA", "VAT", "336", ".va", "Vatican City", "+02:00", "379", "EUR", "€", "Euro", "Cent", "100", "Vatican City", "1:1 (1)", "825"});
        info.put("VC", new String[]{"Saint Vincent and the Grenadines", "Saint Vincent and the Grenadines", "UN member state", "VC", "VCT", "670", ".vc", "Kingstown", "-04:00", "1-784", "XCD", "$", "Eastern Caribbean dollar", "Cent", "100", "Saint Vincent and the Grenadines", "2:3 (1.5)", "110,696"});
        info.put("VE", new String[]{"Venezuela (Bolivarian Republic of)", "The Bolivarian Republic of Venezuela", "UN member state", "VE", "VEN", "862", ".ve", "Caracas", "-04:30", "58", "VES", "Bs.S.or Bs.", "Venezuelan bolívar soberano", "Céntimo", "100", "Venezuela", "2:3 (1.5)", "28,705,000"});
        info.put("VG", new String[]{"Virgin Islands (British)", "The Virgin Islands", "United Kingdom", "VG", "VGB", "092", ".vg", "Road Town", "-04:00", "1-284", "USD", "$", "United States dollar", "Cent", "100", "British Virgin Islands", "1:2 (2)", "30,000"});
        info.put("VI", new String[]{"Virgin Islands (U.S.)", "The Virgin Islands of the United States", "United States", "VI", "VIR", "850", ".vi", "Charlotte Amalie", "-04:00", "1-340", "USD", "$", "United States dollar", "Cent", "100", "United States Virgin Islands", "2:3 (1.5)", "104,000"});
        info.put("VN", new String[]{"Viet Nam", "The Socialist Republic of Viet Nam", "UN member state", "VN", "VNM", "704", ".vn", "Hanoi", "+07:00", "84", "VND", "₫", "Vietnamese đồng", "Hào", "10", "Vietnam", "2:3 (1.5)", "97,580,000"});
        info.put("VU", new String[]{"Vanuatu", "The Republic of Vanuatu", "UN member state", "VU", "VUT", "548", ".vu", "Port Vila", "+11:00", "678", "VUV", "Vt", "Vanuatu vatu", "Cent", "100", "Vanuatu", "19:36 (1.895)", "301,295"});
        info.put("WF", new String[]{"Wallis and Futuna", "The Territory of the Wallis and Futuna Islands", "France", "WF", "WLF", "876", ".wf", "Mata-Utu", "+12:00", "681", "XPF", "₣", "CFP franc", "Centime", "100", "Wallis and Futuna", "2:3 (1.5)", "11,369"});
        info.put("WS", new String[]{"Samoa", "The Independent State of Samoa", "UN member state", "WS", "WSM", "882", ".ws", "Apia", "+13:00", "685", "WST", "T", "Samoan tālā", "Sene", "100", "Samoa", "1:2 (2)", "199,853"});
        info.put("YE", new String[]{"Yemen", "The Republic of Yemen", "UN member state", "YE", "YEM", "887", ".ye", "Sana'a", "+03:00", "967", "YER", "﷼", "Yemeni rial", "Fils", "100", "Yemen", "2:3 (1.5)", "30,491,000"});
        info.put("YT", new String[]{"Mayotte", "The Department of Mayotte", "France", "YT", "MYT", "175", ".yt", "Mamoudzou", "+03:00", "262", "EUR", "€", "Euro", "Cent", "100", "Mayotte", "2:3 (1.5)", "400,201"});
        info.put("ZA", new String[]{"South Africa", "The Republic of South Africa", "UN member state", "ZA", "ZAF", "710", ".za", "Pretoria", "+02:00", "27", "ZAR", "R", "South African rand", "Cent", "100", "South Africa", "2:3 (1.5)", "60,142,978"});
        info.put("ZM", new String[]{"Zambia", "The Republic of Zambia", "UN member state", "ZM", "ZMB", "894", ".zm", "Lusaka", "+02:00", "260", "ZMW", "ZK", "Zambian kwacha", "Ngwee", "100", "Zambia", "2:3 (1.5)", "18,400,556"});
        info.put("ZW", new String[]{"Zimbabwe", "The Republic of Zimbabwe", "UN member state", "ZW", "ZWE", "716", ".zw", "Harare", "+02:00", "263", "ZWE", "Z$", "RTGS dollar", "(none)", "(none)", "Zimbabwe", "1:2 (2)", "15,790,716"});
        return info;
    }
}