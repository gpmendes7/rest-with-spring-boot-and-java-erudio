# Guia de Diagnóstico - Erro de Conexão RDS

## Mudanças Realizadas:
1. ✅ Atualizado `application.yml` para usar `${SPRING_DATASOURCE_URL}` (ao invés de `${JDBC_URL}`)
2. ✅ Adicionado valores padrão (fallback) para desenvolvimento local
3. ✅ Removido `sslMode=VERIFY_IDENTITY` (pode bloquear sem certificado)
4. ✅ Adicionado passo de debug no workflow para verificar variáveis

## Como Investigar o Erro:

### 1. **Verificar Logs do GitHub Actions**
   - Vá para: Repositório > Actions > Último run do workflow
   - Procure pela seção "Debug environment variables in task definition"
   - Confirme se `DB_HOST`, `DB_NAME`, e `DB_USER` estão corretos

### 2. **Verificar Logs do ECS no CloudWatch**
   - Acesse AWS Console > CloudWatch > Log Groups
   - Procure por um log group com nome similar a: `/ecs/rest-with-spring-boot-erudio`
   - Veja a mensagem de erro completa (não apenas "Unknown database")
   - Procure por erros específicos como:
     - `java.sql.SQLNonTransientConnectionException`: Problema de conexão
     - `Access denied for user`: Credenciais inválidas
     - `Communications link failure`: Host não acessível
     - `Cannot load from localhost.localdomain cert store`: Problema de SSL

### 3. **Verificar se o Banco Existe no RDS**
   - AWS Console > RDS > Databases
   - Confirme se o RDS instance `erudio-db-server` existe
   - **Importante**: Verifique qual é o **nome exato do banco** dentro da instância RDS
   - Conecte-se manualmente (localmente ou via EC2):
     ```bash
     mysql -h erudio-db-server.cahsi8yio9fb.us-east-1.rds.amazonaws.com -P 3306 -u root -p
     # Digite a senha: admin123
     # Dentro do MySQL, execute:
     SHOW DATABASES;
     ```
   - O banco `erudio-db-server` deve aparecer na lista

### 4. **Verificar Security Group do RDS**
   - AWS Console > RDS > Databases > Selecionar instância
   - Vá até "Connectivity & security" > "Security groups"
   - Clique no Security Group
   - Vá para "Inbound rules"
   - Confirme se existe uma regra permitindo conexões na porta 3306
   - **Importante**: O Security Group do **ECS** deve estar autorizado (ou "0.0.0.0/0" para aceitar de qualquer lugar)

### 5. **Verificar VPC e Redes**
   - ECS e RDS devem estar na mesma VPC (ou com rotas configuradas)
   - Ambos devem estar na mesma subnet ou em subnets que se comunicam

## Valores Esperados nos Secrets do GitHub:

| Secret | Valor Esperado |
|--------|---|
| `DB_HOST` | `erudio-db-server.cahsi8yio9fb.us-east-1.rds.amazonaws.com` |
| `DB_PORT` | `3306` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | `admin123` |
| `DB_NAME` | **??? (CONFIRME QUAL É O NOME EXATO DO BANCO NO RDS)** |

## Próximos Passos:

1. **Commit e push** das mudanças:
   ```bash
   git add src/main/resources/application.yml
   git add .github/workflows/continuous-deployment.yml
   git commit -m "fix: Use correct datasource variable names and add SSL=false for debugging"
   git push origin main
   ```

2. **Reexecute o workflow** no GitHub Actions

3. **Copie a mensagem de erro completa** dos logs do CloudWatch (veja passo 2 acima)

4. **Compartilhe comigo**:
   - A mensagem de erro exata do ECS/CloudWatch
   - Confirmação de qual é o nome exato do banco no RDS
   - Se existe um Security Group bloqueando

## Dica Importante:
Se o erro for `Unknown database 'erudio-db-server'`, significa que o RDS está acessível, mas o banco não existe. Crie-o com:

```bash
mysql -h erudio-db-server.cahsi8yio9fb.us-east-1.rds.amazonaws.com -P 3306 -u root -p
# Digite a senha: admin123
CREATE DATABASE `erudio-db-server` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Ou use a ferramenta AWS RDS Query Editor.

