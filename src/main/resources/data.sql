-- Insert roles
INSERT INTO role(id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role(id, name) VALUES (2, 'ROLE_USER');

-- Insert movies
INSERT INTO movie(id, available, title, overview, poster_path, duration_in_minutes)
VALUES
    (1, true, 'The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', '/shawshank_redemption_poster.jpg', 142),
    (2, true, 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', '/godfather_poster.jpg', 175),
    (3, true, 'The Dark Knight', 'When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.', '/dark_knight_poster.jpg', 152),
    (4, true, 'Schindler''s List', 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', '/schindlers_list_poster.jpg', 195);

-- Insert cinema rooms
INSERT INTO cinema_room(id, has3d, has_surround, number_of_rows, number_of_columns, base_seat_price)
VALUES
    (1, true, true, 10, 15, 10.0),
    (2, false, true, 8, 12, 8.5),
    (3, true, false, 12, 20, 12.0);

-- Insert showtimes
INSERT INTO showtime(id, start_time, end_time, cinema_room_id, movie_id)
VALUES
    (1, '2024-03-15 18:00:00', '2024-03-16 20:30:00', 1, 1),
    (2, '2024-03-15 20:45:00', '2024-03-16 23:00:00', 2, 2),
    (3, '2024-03-16 15:00:00', '2024-03-16 17:30:00', 3, 3);
