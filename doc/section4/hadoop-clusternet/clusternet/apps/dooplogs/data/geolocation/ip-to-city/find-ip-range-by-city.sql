SELECT 
   L.country_code, L.region, L.city, L.latitude, L.longitude, B.start_int, B.end_int, 
   concat(concat(INET_NTOA(B.start_int)," -> "), INET_NTOA(B.end_int)) as ip_range
FROM
    geocity_block B INNER JOIN geocity_location L ON (B.location_id=L.id)
        INNER JOIN country C ON (L.country_code=C.code)
WHERE
    C.code="BR" AND city LIKE "Macei%"