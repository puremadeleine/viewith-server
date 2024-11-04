-- tb_venue
INSERT INTO tb_venue (venue_id, image_url, location, name) VALUES
                                                               (1, 'http://example.com/image1.jpg', '서울시 구로구', '고척 스카이돔'),
                                                               (2, 'http://example.com/image2.jpg', '서울시 송파구', '잠실실내체육관'),
                                                               (3, 'http://example.com/image3.jpg', '서울시 중구', '장충체육관');
-- tb_seat
INSERT INTO tb_seat (seat_id, seat_column, seat_row, venue_id, section) VALUES
                                                                            (1, 1, 1, 1, 'A'),
                                                                            (2, 2, 1, 1, 'A'),
                                                                            (3, 1, 2, 2, 'B'),
                                                                            (4, 2, 2, 2, 'B');


-- tb_help
INSERT INTO tb_help (help_id, title, content, create_time) VALUES
                                                                (1, '도움말1', '<p>도움말입니도<img src="https://example" alt="이미지"/>도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도</p>', now()),
                                                                (2, '도움말2', '<p>도움말입니도<img src="https://example" alt="이미지"/>도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도</p>', now()),
                                                                (3, '도움말3', '<p>도움말입니도<img src="https://example" alt="이미지"/>도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도도움말입니도</p>', now());


