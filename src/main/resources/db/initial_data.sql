create table if not exists public.roles
(
    id          bigint not null
        primary key,
    description varchar(255),
    name        varchar(255)
);

alter table public.roles
    owner to postgres;

create table if not exists public.users
(
    id         bigint not null
        primary key,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    phone      varchar(255)
);

alter table public.users
    owner to postgres;

create table if not exists public.users_roles
(
    role_id bigint not null
        constraint fkj6m8fwv7oqv74fcehir1a9ffy
            references public.roles,
    user_id bigint not null
        constraint fk2o0jvgh89lemvvo17cbqvdxaa
            references public.users,
    primary key (role_id, user_id)
);

alter table public.users_roles
    owner to postgres;

INSERT INTO public.roles (id, description, name)
VALUES (1, 'User role', 'USER'),
       (2, 'Admin role', 'ADMIN'),
       (3, 'Super Admin role', 'SUPER_ADMIN')
ON CONFLICT (id) DO NOTHING;

-- password123
-- adminpass
-- superpass
INSERT INTO public.users (id, email, first_name, last_name, password, phone)
VALUES (1, 'user@example.com', 'User', 'Margaryan', '$2a$12$1XQKMVp.gyi9gktxaT8cSOsSityoRZMBPQUB7SG7cuDiFcX.GGj62', '123-456-7890'),
       (2, 'admin@example.com', 'Admin', 'User', '$2a$12$c/70brVXcRtC.gvNA0cnpujlYs0l1uHpWURAo2Pe6vpcy6rYoL2Vm', '987-654-3210'),
       (3, 'erickmargarian@gmail.com', 'Erik', 'Margaryan', '$2a$12$OWX8XB7k29peM5MYywRw7e56ucHw5joBrmC.WzjYIroJb8SDVF1ea', '+37498490961')
ON CONFLICT (id) DO NOTHING;

INSERT INTO public.users_roles (role_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3)
ON CONFLICT DO NOTHING;
