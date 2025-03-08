-- Insert interests
INSERT INTO interests (name) VALUES
                                 ('Cricket'), ('Chess'), ('Football'), ('Movies'), ('Tennis'), ('Badminton');

-- Insert users
INSERT INTO users (name, gender, age) VALUES
                                          ('User 1', 'Female', 25),
                                          ('User 2', 'Male', 27),
                                          ('User 3', 'Male', 26),
                                          ('User 4', 'Female', 24),
                                          ('User 5', 'Female', 32);

-- Insert user interests
INSERT INTO user_interests (user_id, interest_id) VALUES
                                                      (1, 1), (1, 2),  -- User 1: Cricket, Chess
                                                      (2, 1), (2, 3), (2, 4),  -- User 2: Cricket, Football, Movies
                                                      (3, 4), (3, 5), (3, 3), (3, 1),  -- User 3: Movies, Tennis, Football, Cricket
                                                      (4, 5), (4, 3), (4, 6),  -- User 4: Tennis, Football, Badminton
                                                      (5, 1), (5, 3), (5, 4), (5, 6);  -- User 5: Cricket, Football, Movies, Badminton
