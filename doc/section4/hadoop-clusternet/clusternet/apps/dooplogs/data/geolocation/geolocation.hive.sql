CREATE TABLE geo_blocks (
  ip_start STRING,
  ip_end STRING,
  location_id STRING)
ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.RegexSerDe'
WITH SERDEPROPERTIES (
  "input.regex" = "(\"[0-9]*\"),(\"[0-9]*\"),(\"[0-9]*\")?",
  "output.format.string" = "%1$s %2$s %3$s"
)
STORED AS TEXTFILE;

ADD FILE geolocation-mapper.py


CREATE TABLE geo_blocks_indexed (
  location_id INT,
  ip_start INT,
  ip_end INT,
  geo_index INT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t';

INSERT OVERWRITE TABLE geo_blocks_indexed
SELECT
  TRANSFORM (ip_start, ip_end, location_id)
  USING 'python geolocation.py'
  AS (location_id, ip_start, ip_end, geo_index)
FROM geo_blocks;
