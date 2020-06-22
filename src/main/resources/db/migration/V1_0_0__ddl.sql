create table product (
    id bigint primary key,
    name varchar(128) not null
);

create table location (
    id  bigint identity primary key,
    name varchar(128) not null
);

create table f_location_product (
    product_id int,
    location_id int,
    qty int default 0 check (qty >= 0),
    price decimal,
    foreign key(product_id) references product(id),
    foreign key(location_id) references location(id),
    primary key (product_id, location_id)
);