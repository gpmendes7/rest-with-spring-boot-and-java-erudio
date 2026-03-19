SELECT id,
	author,
	launch_date AS launchDate,
	price,
	title
FROM rest_with_spring_boot_erudio.books b
JOIN person_books pb ON pb.book_id = b.id
WHERE pb.person_id = 50