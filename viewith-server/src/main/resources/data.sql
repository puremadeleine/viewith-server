-- tb_venue
INSERT INTO tb_venue (venue_id, image_url, location, name) VALUES
                                                               (1, 'http://example.com/image1.jpg', '서울시 구로구', '고척 스카이돔'),
                                                               (2, 'http://example.com/image2.jpg', '서울시 송파구', '잠실실내체육관'),
                                                               (3, 'http://example.com/image3.jpg', '서울시 중구', '장충체육관');
-- tb_seat
INSERT INTO tb_seat (seat_id, seat_column, seat_row, venue_id, section, floor) VALUES
                                                                            (1, 1, 1, 1, 'A', 1),
                                                                            (2, 2, 1, 1, 'A', 1),
                                                                            (3, 1, 2, 2, 'B', 2),
                                                                            (4, 2, 2, 2, 'B', 2);

-- tb_review
INSERT INTO tb_review (RATING, REPORT_COUNT, CREATE_TIME, PERFORMANCE_ID, REVIEW_ID, SEAT_ID, UPDATE_TIME, VENUE_ID, BLOCK, CONTENT, STATUS) VALUES
                        (5.0, 0, '2024-10-22 00:56:11.607361', NULL, 1, 2, '2024-10-22 00:56:11.607361', 1, NULL, '내용', 'NORMAL'),
                        (2.0, 0, '2024-10-22 00:56:13.811936', NULL, 2, 2, '2024-10-22 00:56:13.811936', 1, NULL, '내용', 'NORMAL'),
                        (2.0, 0, '2024-10-22 00:56:38.082731', NULL, 3, 4, '2024-10-22 00:56:38.082731', 1, NULL, '내용', 'NORMAL');
