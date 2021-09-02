drop database if exists capstoneDB;

create database capstoneDB;

use capstoneDB;

create table category (
	category_id int primary key auto_increment,
    category_name varchar(100) not null
);

create table site (
	site_id int primary key auto_increment,
    site_name varchar(100) not null
);

create table sold (
	sold_id int primary key auto_increment,
    sold_name varchar(10) not null
);

create table item (
	item_id int primary key auto_increment,
    category_id int not null,
    item_name varchar(100) not null,
    cost decimal(13,2) not null,
    hours decimal(13,2) not null,
    date_made date not null,
    sold_id int not null,
    notes text null,
    constraint fk_item_category
	  foreign key (category_id)
      references category(category_id),
    constraint fk_item_sold
	  foreign key (sold_id)
      references sold(sold_id)
);

create table sale (
	sale_id int primary key auto_increment,
    item_id int not null,
    price decimal(13,2) not null,
    date_sold date not null,
    site_id int not null,
    constraint fk_sale_item
	  foreign key (item_id)
      references item(item_id),
    constraint fk_sale_site
	  foreign key (site_id)
      references site(site_id)
);

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
    ('Society6');
    
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
	(1, 'Feather Painting', 3.27, 12.00, '2020-03-15', 1, 'Acrylic painting, 8"x10"'),
(1, 'Bill Nye Funko Pop', 9.32, 3.25, '2017-03-04', 2, ''),
(2, 'Brown Sunglasses', 14.56, 3.50, '2020-01-17', 1, ''),
(2, 'Pink Sweater', 9.39, 5.25, '2020-04-01', 2, 'Size Small'),
(3, 'Orange Blanket', 8.90, 2.50, '2017-12-05', 2, ''),
(3, 'Gray Chunky Knit Blanket', 32.00, 2.00, '2020-03-29', 1, '5ft x 4ft'),
(4, 'Red Ring', 5.27, 2.50, '2020-03-12', 2, 'Red gem ring size 7'),
(4, 'Sapphire Necklace', 30.49, 2.50, '2019-12-07', 1, ''),
(1, 'Aang Funko Pop', 5.27, 4.75, '2018-04-22', 2, ''),
(1, 'Landscape Painting', 10.38, 5.75, '2019-12-21', 2, ''),
(1, 'Roman Calligraphy', 20.75, 6.50, '2020-01-18', 1, ''),
(1, 'Clay Fish', 4.98, 2, '2020-02-28', 1, ''),
(1, 'Spiral Pottery', 9.21, 3.25, '2019-03-30', 2, ''),
(1, 'Joker Glitter Art', 13.45, 18.75, '2020-03-14', 2, ''),
(1, 'Singer on Stage Poster', 7.54, 3.25, '2018-08-12', 1, ''),
(1, 'Soccer Ball Glitter Art', 4.56, 9.50, '2019-11-15', 1, ''),
(1, 'Red Sparkle Poster', 9.32 , 2.25, '2014-08-07', 2, '24” by 30”'),
(1, 'Bear with Pepsi Canvas', 4.54, 4.25, '2019-12-15', 2, ''),
(1, 'Stranger Things Drawing', 1.34, 8.25, '2014-09-22', 2, ''),
(1, 'Paris Hilton Funko Pop', 16.78, 2.50, '2014-09-18', 2, ''),
(1, 'Kobe Bryant Canvas', 4.08, 5.25, '2019-04-23', 2, ''),
(1, 'Cat Playing with Yarn Drawing', 2.47, 8.50, '2013-03-19', 2, ''),
(1, 'Clay Fisherman', 18.63, 2.00, '2019-10-20', 1, ''),
(1, 'Board Game Poster', 6.78, 7.50, '2019-01-02', 2, ''),
(1, 'Vase Pottery', 2.34, 8.75, '2020-02-08', 1, ''),
(1, 'Will Byers Glitter Art', 7.84, 13.25, '2019-10-22', 2, ''),
(1, 'A Beautiful Mind Poster', 0.59, 1.50, '2019-09-09', 2, ''),
(1, 'Boat at Dock Drawing', 2.94, 3.50, '2017-05-10', 2, ''),
(1, 'Whiskers Funko Pop', 4.45, 12.00, '2020-02-12', 2, ''),
(1, 'Coraline Poster', 7.76, 5.25, '2018-09-25', 2, ''),
(1, 'Butterfly Canvas', 3.27, 3.50, '2018-03-30', 2, ''),
(1, 'Apple Drawing', 7.80, 1.25, '2019-03-12', 1, ''),
(1, 'Zuko Funko Pop', 5.76, 3.00, '2019-03-02', 2, ''),
(1, 'Joe Exotic Funko Pop', 9.32, 8.25, '2020-01-14', 2, ''),
(1, 'iPhone Glitter Art', 4.78, 3.75, '2019-12-14', 1, ''),
(1, 'Gnome Poster ', 2.34, 9.00, '2020-01-19', 1, ''),
(1, 'Billy with Trike Canvas', 3.69, 12.25, '2017-03-30', 2, ''),
(1, 'Michael Myers Funko Pop', 13.21, 13.25, '2019-09-12', 1, ''),
(1, 'Freddy Krueger Canvas', 4.32, 4.75, '2019-05-09', 2, ''),
(1, 'Kylie Jenner Poster', 1.15, 18.50, '2020-02-03', 2, ''),
(1, 'Spiderman with Web Drawing', 2.34, 3.25, '2019-01-17', 2, ''),
(1, 'Pizza Canvas', 7.98, 2.50, '2020-02-02', 1, ''),
(1, 'Clouds Pottery', 8.12, 1.50, '2019-03-04', 2, ''),
(1, 'Star Wars Poster', 1.87, 7.50, '2019-02-19', 2, ''),
(1, 'Keanu Reeves Funko Pop', 19.32, 3.50, '2020-01-29', 1, ''),
(1, 'Waterfall Canvas', 4.78, 12.00, '2019-10-02', 2, ''),
(1, 'The Goonies Poster', 8.72, 6.75, '2018-12-24', 2, ''),
(2, 'Purple Winter Gloves', 12.34, 3.50, '2019-03-22', 2, ''),
(2, 'Pink Faux Leather Jacket', 17.87, 4.25, '2018-05-24', 2, ''),
(2, 'Rain Coat', 11.12, 4.75, '2017-07-01', 2, ''),
(2, 'Blue Striped Shirt', 2.45, 3.00, '2017-12-30', 2, ''),
(2, 'Cat Eye Sunglasses', 7.89, 4.50, '2020-01-29', 1, ''),
(2, 'Black Ruffle Skirt', 12.35, 2.50, '2018-07-15', 2, ''),
(2, 'Winter Gloves', 12.20, 3.25, '2019-10-18', 1, ''),
(2, 'Maroon Skirt', 4.34, 4.50, '2018-09-19', 2, ''),
(2, 'Green Army Jacket', 6.67, 8.50, '2019-02-04', 2, ''),
(2, 'Blue Rimmed Sunglasses', 3.12, 1.50, '2019-06-05', 2, ''),
(2, 'Navy Winter Coat', 9.53, 4.25, '2020-02-27', 1, ''),
(2, 'Red Pants', 5.90, 5.00, '2018-05-22', 2, ''),
(2, 'Tropical Shorts', 4.56, 1.50, '2018-02-28', 2, ''),
(2, 'Minnesota Vikings Jacket', 12.34, 12.25, '2019-07-12', 2, ''),
(2, 'Cat Socks', 1.42, 1.25, '2019-10-05', 2, ''),
(2, 'Light Blue Coat', 11.32, 8.50, '2019-08-02', 1, ''),
(2, 'Yellow Polka Dot Shirt', 9.78, 5.75, '2018-02-05', 2, ''),
(2, 'Swirl Jacket', 23.45, 2.50, '2019-09-24', 1, ''),
(2, 'Green Gloves', 8.90, 1.25, '2019-11-28', 2, ''),
(2, 'Ripped Jeans', 12.37, 8.50, '2019-05-22', 2, ''),
(2, 'Purple Long Sleeve Shirt', 6.45, 3.25, '2018-11-21', 2, ''),
(2, 'Green Sunglasses', 7.46, 12.50, '2018-10-17', 2, ''),
(2, 'Skinny Jeans', 8.67, 16.75, '2019-06-23', 2, ''),
(2, 'Teal Shorts', 3.45, 2.50, '2019-03-04', 2, ''),
(3, 'Large Furry Blanket', 20.56, 5.50, '2019-05-01', 2, ''),
(3, 'Extra Stuffed Pillow', 12.31, 1.25, '2017-03-25', 2, ''),
(3, 'Purple Rug', 4.56, 7.00, '2020-02-27', 1, ''),
(3, 'Yellow Fluffy Pillow', 8.45, 5.50, '2017-06-06', 2, ''),
(3, 'Purple Blanket', 10.23, 4.75, '2018-10-08', 2, ''),
(3, 'Flower Rug', 19.76, 7.00, '2019-08-12', 2, ''),
(3, 'Orange Vase', 8.56, 1.50, '2020-03-25', 1, ''),
(3, 'Circle Decorative Pillow', 7.67, 0.50, '2017-02-17', 2, ''),
(3, 'Gray Rug', 4.67, 2.25, '2019-01-19', 2, ''),
(3, 'Soft Baby Blanket', 3.45, 12.50, '2020-01-20', 1, ''),
(3, 'Purple Knitted Blanket', 23.23, 2.75, '2019-04-22', 2, ''),
(3, 'Oval Accent Mirror', 7.65, 2.00, '2018-02-23', 2, ''),
(3, 'Chunky Knit Pillowcase ', 12.45, 1.00, '2020-01-18', 1, ''),
(3,  'Square Mirror ', 7.34, 2.25, '2020-01-30', 1, ''),
(3, 'Black White Accent Rug', 10.34, 2.00, '2018-10-12', 2, ''),
(3, 'Rustic Mirror Set', 7.65, 3.50, '2020-01-09', 2, ''),
(3, 'Chunky Knit Blanket', 35.34, 3.50, '2020-04-05', 1, ''),
(3, 'Blue Fluffy Rug', 23.17, 2.50, '2019-03-01', 2, ''),
(3, 'Purple Furry Blanket', 18.43, 12.75, '2018-09-02', 2, ''),
(3, 'Red Cross Blanket', 3.12, 2.00, '2019-11-07', 1, ''),
(3, 'White Rug', 15.67, 4.50, '2019-12-19', 2, ''),
(3, 'Rustic Vase Set', 7.34, 5.50, '2019-04-22', 2, ''),
(3, 'Teal Pillowcase', 5.45, 0.50, '2020-03-03', 1, ''),
(3, 'Large White Pillowcase', 5.89, 0.75, '2017-09-21', 2, ''),
(3, 'Brown Shaggy Rug', 15.99, 7.75, '2018-01-30', 2, ''),
(3, 'Short Ceramic Vase', 5.32, 0.50, '2020-04-12', 1, ''),
(3, 'Green Pillow', 2.90, 1.25, '2018-09-13', 2, ''),
(3, 'Tall Glass Vase', 4.56, 1.50, '2019-04-22', 2, ''),
(3, 'Furry Green Blanket', 8.45, 4.00, '2018-02-14', 2, ''),
(4, 'Black Gem Necklace', 4.56, 4.50, '2018-06-04', 2, ''),
(4, 'Colorful Bracelet', 1.45, 0.75, '2020-01-02', 1, ''),
(4, 'Green Bracelet', 1.23, 1.00, '2018-05-07', 2, ''),
(4, 'Glitter Ring', 4.56, 0.50, '2019-02-27', 2, ''),
(4, 'Water Tribe Necklace', 3.90, 1.25, '2018-01-12', 2, ''),
(4, 'Purple Bracelet', 5.67, 0.75, '2020-01-15', 1, ''),
(4, 'Silver Crown', 12.45, 3.50, '2018-01-15', 2, ''),
(4, 'Green Armband', 1.67, 2.00, '2017-02-12', 2, ''),
(4, 'Green Ring', 4.45, 1.50, '2020-01-09', 1, ''),
(4, 'Metal Necklace', 6.26, 0.75, '2020-02-04', 1, ''),
(4, 'Shell Anklet', 3.12, 0.50, '2020-02-02', 1, ''),
(4, 'Emerald Ring', 8.23, 1.50, '2018-02-27', 2, ''),
(4, 'Pink Anklet', 4.89, 1.25, '2017-08-29', 2, ''),
(4, 'Golden Crown', 12.45, 5.75, '2019-03-30', 2, ''),
(4, 'MultiColored Necklace', 6.65, 2.50, '2017-08-12', 2, ''),
(4, 'Purple Bracelet', 2.34, 1.50, '2020-02-02', 2, ''),
(4, 'Fancy Ring', 7.89, 4.50, '2019-03-15', 2, ''),
(4, 'Blue Earrings', 6.31, 1.50, '2019-07-21', 2, ''),
(4, 'Flower Ring', 5.67, 3.75, '2018-09-22', 2, ''),
(4, 'Cloud Necklace', 8.32, 1.50, '2019-10-12', 2, ''),
(4, 'Yellow Bracelet', 4.56, 1.00, '2019-05-09', 2, ''),
(4, 'Flower Crown', 9.23, 1.75, '2018-02-25', 2, ''),
(4, 'Unique Ring', 6.43, 2.50, '2019-10-01', 2, ''),
(4, 'Circle Diamond Earrings', 12.46, 4.75, '2019-01-14', 2, ''),
(2, 'Pink Turtleneck', 12.46, 1.75, '2020-02-14', 1, ''),
(2, 'White Tee Shirt', 2.46, 0.75, '2020-04-01', 1, '');

insert into sale (
    item_id,
    price,
    date_sold,
    site_id
) values 
(2, 19.99, '2017-03-25', 4),
(4, 29.99, '2020-04-19', 1),
(5, 39.99, '2018-02-19', 5),
(7, 17.99, '2020-04-20', 3),
(9, 17.99, '2018-05-28', 2),
(10, 32.99, '2020-01-17', 3),
(13, 32.99, '2019-10-17', 3),
(14, 65.99, '2020-03-29', 3),
(17, 17.99, '2014-09-20', 5),
(18, 24.99, '2020-02-17', 5),
(19, 15.99, '2014-02-20', 3),
(20, 60.99, '2014-12-02', 3),
(21, 22.99, '2020-04-19', 2),
(22, 13.99, '2013-04-17', 5),
(24, 13.99, '2019-10-16', 2),
(26, 35.99, '2019-11-18', 3),
(27, 15.99, '2019-11-12', 2),
(28, 19.99, '2018-04-17', 5),
(29, 24.99, '2020-03-01', 3),
(30, 16.99, '2018-10-15', 2),
(31, 29.99, '2018-04-19', 5),
(33, 15.99, '2019-05-29', 3),
(34, 15.99, '2020-02-17', 1),
(37, 25.99, '2017-06-03', 5),
(39, 23.99, '2019-09-29', 3),
(40, 15.99, '2020-03-17', 3),
(41, 13.99, '2019-12-29', 5),
(43, 20.99, '2020-01-03', 3),
(44, 16.99, '2019-10-17', 1),
(46, 32.99, '2020-04-17', 5),
(47, 13.99, '2019-04-17', 2),
(48, 26.99, '2019-09-13', 4),
(49, 79.99, '2019-01-09', 2),
(50, 29.99, '2017-09-01', 1),
(51, 21.99, '2018-04-17', 4),
(53, 27.99, '2018-12-02', 4),
(55, 29.99, '2018-12-17', 1),
(56, 55.99, '2019-04-12', 2),
(57, 17.99, '2019-07-30', 2),
(59, 25.99, '2018-12-02', 2),
(60, 21.99, '2018-05-22', 1),
(61, 55.99, '2019-09-25', 4),
(62, 13.99, '2020-02-02', 2),
(64, 19.99, '2018-07-17', 4),
(66, 25.99, '2019-12-01', 3),
(67, 54.99, '2019-08-30', 4),
(68, 22.99, '2019-01-17', 1),
(69, 25.99, '2019-05-16', 4),
(70, 60.99, '2019-09-13', 4),
(71, 25.99, '2019-04-17', 1),
(72, 89.99, '2019-09-30', 2),
(73, 30.99, '2017-06-17', 4),
(75, 25.99, '2017-09-09', 1),
(76, 55.99, '2018-12-01', 3),
(77, 55.99, '2019-10-15', 1),
(79, 25.99, '2017-07-07', 2),
(80, 35.99, '2019-04-04', 2),
(82, 52.99, '2019-05-01', 3),
(83, 27.99, '2018-04-08', 1),
(86, 35.99, '2018-12-28', 1),
(87, 35.99, '2020-02-17', 2),
(89, 67.99, '2019-05-05', 3),
(90, 50.99, '2018-12-17', 2),
(92, 50.99, '2020-03-29', 3),
(93, 25.99, '2019-11-17', 4),
(95, 21.99, '2017-12-17', 1),
(96, 32.99, '2018-03-01', 2),
(98, 13.99, '2019-01-02', 2),
(99, 15.99, '2019-10-10', 1),
(100, 29.99, '2018-04-17', 1),
(101, 21.99, '2018-07-01', 4),
(103, 13.99, '2018-09-01', 2),
(104, 10.99, '2019-04-17', 2),
(105, 29.99, '2018-03-01', 3),
(107, 51.99, '2018-08-15', 3),
(108, 9.99, '2017-05-01', 4),
(112, 21.99, '2018-03-01', 4),
(113, 13.99, '2017-11-01', 1),
(114, 39.99, '2019-05-01', 3),
(115, 29.99, '2017-12-12', 1),
(116, 13.99, '2020-04-04', 2),
(117, 29.99, '2019-09-19', 4),
(118, 19.99, '2019-10-11', 2),
(119, 14.99, '2019-04-13', 3),
(120, 29.99, '2019-12-27', 1),
(121, 13.99, '2019-10-04', 2),
(122, 42.99, '2018-05-16', 3),
(123, 12.99, '2019-12-03', 1),
(124, 32.99, '2019-04-20', 2);

create table `user`(
`id` int primary key auto_increment,
`username` varchar(30) not null unique,
`password` varchar(100) not null,
`enabled` boolean not null);

create table `role`(
`id` int primary key auto_increment,
`role` varchar(30) not null
);

create table `user_role`(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`),
foreign key (`user_id`) references `user`(`id`),
foreign key (`role_id`) references `role`(`id`));

insert into `user`(`id`,`username`,`password`,`enabled`)
    values(1,"admin", "password", true),
        (2,"user","password",true);

insert into `role`(`id`,`role`)
    values(1,"ROLE_ADMIN"), (2,"ROLE_USER");
    
insert into `user_role`(`user_id`,`role_id`)
    values(1,1),(1,2),(2,2);
    
    UPDATE user SET password = '$2a$10$S8nFUMB8YIEioeWyap24/ucX.dC6v9tXCbpHjJVQUkrXlrH1VLaAS' WHERE id = 1;
UPDATE user SET password = '$2a$10$S8nFUMB8YIEioeWyap24/ucX.dC6v9tXCbpHjJVQUkrXlrH1VLaAS' WHERE id = 2;