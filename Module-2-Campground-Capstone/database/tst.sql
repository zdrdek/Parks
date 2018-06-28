SELECT site.*, campground.daily_fee
FROM site
JOIN campground
ON campground.campground_id = site.campground_id
WHERE  IN(SELECT 


('2018-06-26' NOT BETWEEN reservation.from_date AND reservation.to_date) AND 
        ('2018-06-30' NOT BETWEEN reservation.from_date AND reservation.to_date) AND 
        (reservation.from_date NOT BETWEEN '2018-06-26' AND '2018-06-30') AND 
        (reservation.to_date NOT BETWEEN '2018-06-26' AND '2018-06-30') AND reservation.site_id = 1