USE capstoneDB;

insert into category (category_name) values
	('Art'),
    ('Clothing'),
    ('Home Decor'),
	('Jewelry');
    
insert into site (site_name) values
	('Amazon'),
    ('eBay'),
    ('Etsy'),
    ('Poshmark'),
    ('Society6'),
	('Zazzle');
    
insert into sold (sold_id, sold_name) values
	(1, 'Available'),
    (2, 'Sold');
    
insert into item (
    category_id,
    item_name,
    cost,
    hours,
    date_made,
    sold_id,
    notes
) values 
	(4, 'Red Ring', 5.27, 2.50, '2020-03-12', 2, 'Red gem ring size 7'),
    (1, 'Feather Painting', 3.27, 12.00, '2020-03-15', 1, 'Acrylic painting, 8"x10"'),
    (3, 'Gray Chunky Knit Blanket', 32.00, 2.00, '2020-03-29', 1, '5ft x 4ft'),
    (2, 'Pink Sweater', 9.39, 5.25, '2020-04-01', 2, 'Size Small');

insert into sale (
    item_id,
    price,
    date_sold,
    site_id
) values 
	(1, 19.99, '2020-03-19', 3),
    (4, 29.99, '2020-04-19', 4);