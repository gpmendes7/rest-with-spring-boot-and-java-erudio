SELECT 
    id,
    CONCAT(first_name, ' ', last_name) AS name,
    address,
    gender,
    enabled
FROM 
    rest_with_spring_boot_erudio.person