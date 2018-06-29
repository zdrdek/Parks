SELECT DISTINCT site.site_id, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, campground.daily_fee
FROM site
JOIN campground
ON campground.campground_id = site.campground_id
WHERE campground.campground_id = 1 AND site.site_id NOT IN(SELECT site.site_id
                                                     FROM site
                                                     JOIN reservation
                                                     ON site.site_id = reservation.site_id
                                                     WHERE '2018-06-22' <= reservation.to_date
                                                     AND '2018-06-26' >= reservation.from_date)
LIMIT 5;
