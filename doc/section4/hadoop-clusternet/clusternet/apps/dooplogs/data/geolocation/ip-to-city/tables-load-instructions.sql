/**
 * Creates the table used to load the CVN file Location.cvs

  mysqlimport --fields-terminated-by="," --fields-optionally-enclosed-by="\"" --lines-terminated-by="\n" --ignore-lines=2 --host=localhost  --user=marcello  --password=marcello  geoips  geocity_location.csv

 * geoips -> database to be first created. (CREATE TABLE geoips) use regular encoding
 * geocity_location.csv -> csv file renamed from the GeoLiteCityCSV.zip, Location.csv
 * 
 * The city and region might be in latin1 encoding, so setting the column
 * http://dev.mysql.com/doc/refman/5.0/en/charset-we-sets.html
 * http://dev.mysql.com/doc/refman/5.0/en/charset-table.html
 */
CREATE TABLE geocity_location (
    id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT,
    country_code CHAR(2) NOT NULL,
    region VARCHAR(100) CHARACTER SET latin1 NULL,
    city VARCHAR(100) CHARACTER SET latin1 NULL,
    zip_code VARCHAR(15) NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    metro_code SMALLINT NULL,
    area_code SMALLINT NULL
);

/**
 * Creates the table used to load the CVN file Location.cvs

  mysqlimport --fields-terminated-by="," --fields-optionally-enclosed-by="\"" --lines-terminated-by="\n" --ignore-lines=2 --host=localhost --user=marcello  --password=marcello geoips geocity_block.csv

 * geoips -> database to be first created. (CREATE TABLE geoips) use regular encoding
 * geocity_block.csv -> csv file renamed from the GeoLiteCityCSV.zip, Blocks.csv
 * 
 */
CREATE TABLE geocity_block (
    start_int INT UNSIGNED NOT NULL,
    end_int INT UNSIGNED NOT NULL,
    location_id INT UNSIGNED NOT NULL
);
