/**
 * The GeoCountry table is the one used to load the CSV file with the shell command:

   mysqlimport --fields-terminated-by="," --fields-optionally-enclosed-by="\""  --lines-terminated-by="\n"  --host=localhost  --user=marcello  --password=marcello  geoips  geocountry.csv

 * geoips -> database to be first created. (CREATE TABLE geoips) use regular encoding
 * geocountry.csv -> csv file renamed from the GeoIPCountryCSV.zip
 * 
 * http://bafford.com/2009/03/09/mysql-performance-benefits-of-storing-integer-ip-addresses/
 * This is the way Android persists data.
 */
CREATE TABLE geocountry (
    start_ip CHAR(15) NOT NULL,
    end_ip CHAR(15) NOT NULL,
    start_int INT UNSIGNED NOT NULL,
    end_int INT UNSIGNED NOT NULL,
    code CHAR(2) NOT NULL,
    name VARCHAR(50) NOT NULL
);

/**
 * The country table is a list of all the countries from the list and populated by 
 * INSERT INTO country SELECT DISTINCT NULL,code,name FROM geocountry
 */
CREATE TABLE country (
    id TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    code CHAR(2) NOT NULL,
    name VARCHAR(50) NOT NULL
);

/**
 * The GeoIP table with the list of the countries and the start and end IP representation in integers.
 * INSERT INTO geoip SELECT id, start_int, end_int FROM geocountry NATURAL JOIN country
 */
CREATE TABLE geoip (
    country_ip TINYINT UNSIGNED NOT NULL,
    start_int INT UNSIGNED NOT NULL,
    end_int INT UNSIGNED NOT NULL
);