CREATE TABLE IF NOT EXISTS review (
  id          INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  rating      INT NOT NULL,
  review_text TEXT,
  PRIMARY KEY (id)
);
