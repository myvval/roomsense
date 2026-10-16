SELECT app_user.name, reservation.start_at, reservation.end_at, reservation.status_code
FROM app_user
INNER JOIN reservation ON app_user.id = reservation.user_id
WHERE reservation.room_id = 1
AND reservation.start_at >= '2026-10-19'
AND reservation.start_at <  '2026-10-20'
ORDER BY reservation.start_at ASC;

SELECT app_user.name, COUNT(reservation.id) as reservation_count
FROM app_user
LEFT JOIN reservation ON app_user.id = reservation.user_id
GROUP BY app_user.id, app_user.name;
