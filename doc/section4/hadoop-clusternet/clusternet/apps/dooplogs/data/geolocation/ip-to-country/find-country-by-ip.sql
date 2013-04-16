SELECT
    country.code, country.name, geoip.start_int, geoip.end_int, '76.126.154.95' AS ip, INET_ATON('76.126.154.95') AS ip_number
FROM
    country INNER JOIN geoip ON country.id=geoip.country_id
WHERE
    INET_ATON('76.126.154.95') BETWEEN geoip.start_int AND geoip.end_int