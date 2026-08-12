CREATE TABLE IF NOT EXISTS products (id BIGSERIAL PRIMARY KEY,name VARCHAR(150) NOT NULL,category VARCHAR(80) NOT NULL,price NUMERIC(15,2) NOT NULL,active BOOLEAN NOT NULL DEFAULT TRUE);
CREATE TABLE IF NOT EXISTS orders (id BIGSERIAL PRIMARY KEY,created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE IF NOT EXISTS order_items (id BIGSERIAL PRIMARY KEY,order_id BIGINT NOT NULL REFERENCES orders(id),product_id BIGINT NOT NULL REFERENCES products(id),quantity INTEGER NOT NULL CHECK(quantity>0));
INSERT INTO products(name,category,price,active) VALUES ('Espresso Machine','Coffee',450.00,true),('Pour Over Set','Coffee',75.00,true),('Travel Mug','Accessories',30.00,true),('Coffee Grinder','Coffee',180.00,true) ON CONFLICT DO NOTHING;
INSERT INTO orders DEFAULT VALUES;
INSERT INTO order_items(order_id,product_id,quantity) SELECT 1,id,5 FROM products WHERE name='Espresso Machine';
INSERT INTO order_items(order_id,product_id,quantity) SELECT 1,id,12 FROM products WHERE name='Pour Over Set';
