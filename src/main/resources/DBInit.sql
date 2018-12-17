-- roles table generation
create table roles
(
  role_id integer not null
    constraint roles_pkey
      primary key,
  nm      varchar(10)
);

alter table roles
  owner to "Welcome9";

INSERT INTO public.roles (role_id, nm) VALUES (1, 'admin');
INSERT INTO public.roles (role_id, nm) VALUES (2, 'cardholder');


-- users table generation

create table users
(
  frst_nm varchar(10),
  last_nm varchar(10),
  sso_id  varchar(40) not null
    constraint users_pkey1
      primary key
);

alter table users
  owner to "Welcome9";

INSERT INTO public.users (frst_nm, last_nm, sso_id) VALUES ('Jack', 'Smith', '2');
INSERT INTO public.users (frst_nm, last_nm, sso_id) VALUES ('Sunny', 'Wang', '1');


-- users_roles table generation

create table users_roles
(
  sso_id  varchar(40)
    constraint users_roles_users_sso_id_fk
      references users,
  role_id integer
    constraint users_roles_roles_role_id_fk
      references roles
);

alter table users_roles
  owner to "Welcome9";

INSERT INTO public.users_roles (sso_id, role_id) VALUES ('2', 2);
INSERT INTO public.users_roles (sso_id, role_id) VALUES ('1', 1);
INSERT INTO public.users_roles (sso_id, role_id) VALUES ('1', 2);
  

-- prmsns table generation 
  
create table prmsns
(
  descr   varchar(20) not null,
  perm_id integer     not null
    constraint prmsns_pk
      primary key
);

alter table prmsns
  owner to "Welcome9";

create unique index prmsns_perm_id_uindex
  on prmsns (perm_id);

INSERT INTO public.prmsns (descr, perm_id) VALUES ('add user', 1);
INSERT INTO public.prmsns (descr, perm_id) VALUES ('delete user', 2);
INSERT INTO public.prmsns (descr, perm_id) VALUES ('update user', 3);
INSERT INTO public.prmsns (descr, perm_id) VALUES ('browse transactions', 4);

-- roles_prmsns table generation 

create table roles_prmsns
(
  perm_id integer not null,
  role_id integer not null,
  constraint roles_prmsns_pk
    unique (perm_id, role_id)
);

alter table roles_prmsns
  owner to "Welcome9";

INSERT INTO public.roles_prmsns (perm_id, role_id) VALUES (1, 1);
INSERT INTO public.roles_prmsns (perm_id, role_id) VALUES (2, 1);
INSERT INTO public.roles_prmsns (perm_id, role_id) VALUES (3, 1);
INSERT INTO public.roles_prmsns (perm_id, role_id) VALUES (4, 2);
