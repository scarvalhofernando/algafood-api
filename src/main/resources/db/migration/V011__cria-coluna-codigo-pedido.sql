ALTER table pedido add codigo varchar(36) not null after id;
UPDATE pedido SET codigo = uuid();
ALTER table pedido add constraint uk_pedido_codigo unique (codigo);