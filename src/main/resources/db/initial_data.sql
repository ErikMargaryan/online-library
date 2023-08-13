INSERT INTO library.public.roles (id, description, name)
VALUES (1, 'User role', 'USER'),
       (2, 'Admin role', 'ADMIN'),
       (3, 'Super Admin role', 'SUPER_ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO library.public.users (id, email, first_name, last_name, password, phone)
VALUES (1, 'user@example.com', 'User', 'Margaryan', 'password123', '123-456-7890'),
       (2, 'admin@example.com', 'Admin', 'User', 'adminpass', '987-654-3210'),
       (3, 'erickmargarian@gmail.com', 'Erik', 'Margaryan', 'superpass', '+37498490961')
ON CONFLICT (id) DO NOTHING;;

INSERT INTO library.public.users_roles (role_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3)
ON CONFLICT DO NOTHING;;
