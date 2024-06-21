# ProvaAV2

# Introdução ao codigo

Esse é um codigo que foi desenvolvido para autenticar e autorizar diferentes tipos de usuarios, dando permissões e restringindo acesso a diferentes funcionalidades. É uma pagina Web (Java SpringBoot) e que faz requisições a um banco de dados (Mongo DB) e utiliza RestApi e TokensJWT. Abaixo explicarei cada funcionalidade das minhas classes.

## PASTA APPLICATION

### CLASSE EcommerceApplication(APPLICATION)

![ecommerceApplication.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/d0ac3a08-1523-445d-b47b-907a4676f03d/ecommerceApplication.png)

**Descrição**:

- **Anotação @SpringBootApplication**: Marca a classe como uma aplicação Spring Boot.
- **Anotação @EnableMongoRepositories**: Habilita a detecção de repositórios do MongoDB.
- **Método main**: Ponto de entrada da aplicação.

## PASTA CONFIG

### CLASSE AppCongig(CONFIG)

![AppConfig.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/9af04792-a38e-4498-af38-99d131b43b3f/AppConfig.png)

**Descrição**:

- **Anotação @Configuration**: Marca a classe como uma fonte de definições de beans.
- **Anotação @EnableWebSecurity**: Habilita a segurança web do Spring Security.
- **Método securityFilterChain(HttpSecurity http)**: Configura as regras de segurança HTTP.
    - **csrf(AbstractHttpConfigurer::disable)**: Desabilita a proteção CSRF.
    - **authorizeHttpRequests(request -> request...)**: Define regras de autorização para diferentes endpoints.
    - **addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)**: Adiciona o filtro de autenticação JWT antes do filtro de autenticação padrão.
- **Método passwordEncoder()**: Define o bean para o codificador de senhas BCrypt.

## PASTA CONTROLLER

### CLASSE AuthController(CONTROLLER)

![AuthController.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/77607b80-b3d1-457e-b6ba-06f008f1e8cc/AuthController.png)

**Descrição**:

- **Anotação @RestController**: Marca a classe como um controlador REST.
- **Método register(@RequestBody User user)**: Registra um novo usuário.
- **Método login(@RequestParam String username, @RequestParam String password)**: Autentica o usuário e retorna um token JWT.
- **Método extractRole(@PathVariable String token)**: Extrai a role do token JWT.

## PASTA MODEL

### CLASSE User(MODEL)

![usercode.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/25aa8160-6f5b-4e8f-96f0-d8a4ecdc9ae5/usercode.png)

**Descrição**:

- **Anotação @Document**: Marca a classe como um documento MongoDB.
- **Atributos**: `id`, `username`, `password`, `role` - representam os campos do documento de usuário.
- **Getters e Setters**: Métodos para acessar e modificar os atributos.

## PASTA REPOSITORY

### CLASSE UserRepostirory(REPOSITORY)

![UserRepository.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/bc08d859-7f97-4607-a030-9cbbf60c4e10/UserRepository.png)

**Descrição**:

- **Anotação @Repository**: Marca a interface como um repositório do Spring Data.
- **Interface MongoRepository<User, String>**: Extende a interface MongoRepository fornecida pelo Spring Data, especificando a entidade `User` e o tipo do ID (`String`).
- **Método findByUsername(String username)**: Declaração de um método personalizado para encontrar um usuário pelo nome de usuário.

## PASTA SECURITY

### CLASSE JwtAuthenticationFilter(SECURITY)

![JwtAuthenticationFilter.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/de68bcc0-1658-4741-b26a-d250ea546641/JwtAuthenticationFilter.png)

**Descrição**:

- **Anotação @Component**: Marca a classe como um componente do Spring.
- **Método doFilterInternal**: Filtra as requisições HTTP para validar o token JWT e autenticar o usuário.
- **Autenticação do Usuário**: Extração do nome de usuário do token JWT e configuração do contexto de segurança.

### CLASSE JwtUtil(SECURITY)

![JwtUtil.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/bfb7edbd-b90e-41cd-864a-757c175eea1c/JwtUtil.png)

![JwtUtil2.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/824def37-b12f-4ca9-a96b-10f233a2cb31/JwtUtil2.png)

**Descrição**:

- **@Component**: Marca a classe como um bean gerenciado pelo Spring.
- **Construtor JwtUtil(@Value("${jwt.secret}") String secret)**: Inicializa a chave secreta usada para assinar os tokens JWT.
- **generateToken(String username, String role)**: Gera um token JWT com o nome de usuário e a role.
- **extractUsername(String token)**: Extrai o nome de usuário do token JWT.
- **extractRole(String token)**: Extrai a role do token JWT.

## PASTA SERVICE

### CLASSE AuthService(SERVICE)

![AuthService.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/c40012b5-54b8-4832-af74-dea7dd09def5/AuthService.png)

**Descrição**:

- **@Service**: Marca a classe como um serviço do Spring.
- **authenticateUser(String username, String password)**: Autentica o usuário verificando as credenciais e gera um token JWT.
- **extractUsername(String token)**: Extrai o nome de usuário do token JWT.
- **extractRole(String token)**: Extrai a role do token JWT.

### CLASSE UserService(SERVICE)

![UserService.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/c2888f5a-2715-468b-9215-40c9a1069754/UserService.png)

**Descrição**:

- **@Service**: Marca a classe como um serviço do Spring.
- **registerUser(User user)**: Registra um novo usuário após verificar se o nome de usuário já existe e codificar a senha.

## PASTA TEST

### CLASSE SecretKeyGenerator(TEST)

![SecretKeyGenerator.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/6543687b-4a8a-4878-8353-201edc7a4b0d/SecretKeyGenerator.png)

**Descrição**:

- **Classe para Gerar Chave Secreta**: Utilizada para gerar uma chave secreta para assinatura de tokens JWT.
- **Método main**: Gera e imprime uma chave secreta codificada em base64.

# DIAGRAMA

![diagrama.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/975f6509-56be-4a19-9341-946485d69fd7/diagrama.png)

![diagrama2.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/b438f759-a926-4a88-a222-169aa58f53f6/diagrama2.png)

### Explicação Passo a Passo do Diagrama

1. **JwtRestapiApplication**: Ponto de entrada da aplicação Spring Boot.
2. **AppConfig**: Configura as regras de segurança, define os beans de segurança e adiciona o filtro JWT.
3. **AuthController**: Controlador responsável por gerenciar as requisições de registro, login e extração de roles. Chama o `UserService` para registrar usuários e o `AuthService` para autenticar e extrair roles.
4. **AuthService**: Contém a lógica de autenticação e interação com o repositório de usuários. Utiliza o `JwtUtil` para gerar e validar tokens JWT.
5. **UserService**: Gerencia o registro de usuários, verificando a existência de nomes de usuário e codificando senhas antes de salvar.
6. **UserRepository**: Repositório que interage com o banco de dados MongoDB para operações de CRUD de usuários. Implementa métodos personalizados como `findByUsername`.
7. **User**: Modelo que representa um usuário no sistema, com atributos `id`, `username`, `password` e `role`.
8. **JwtUtil**: Utilitário que gerencia a geração, extração e validação de tokens JWT.
9. **JwtAuthenticationFilter**: Filtra as requisições HTTP para validar tokens JWT e autenticar usuários.
10. **SecretKeyGenerator**: Classe utilizada para gerar uma chave secreta para assinatura de tokens JWT.
11. **Web Application**: Consiste em páginas HTML (`login.html`, `register.html`, `home.html`) que interagem com o backend para registro, login e acesso a diferentes seções baseadas na role do usuário.

# INCIAÇÃO DO CODIGO

Para se iniciar a aplicação execute primeiro a classe de **SecretKeyGenerator** e gere uma token, logo depois vá até a classe **Application.Properties**  e dentro do metodo `jwt.secret=` coloque a chave gerada. Depois execute a classe `JwtRestapiApplication` e na sua URL digite http://localhost:8080/register.html e faça um novo registro.

# TELAS DA MINHA APLICAÇÃO

## Register.html

![register.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/7c48ede2-d46b-4e90-90a1-d549bcf30146/register.png)

- **register.html**: Página de registro que permite ao usuário criar uma nova conta.
- **Formulário de Registro**: Envia uma requisição POST para `/register` com o nome de usuário, senha e role.
- **Script JavaScript**: Processa a resposta do servidor e redireciona o usuário para a página de login.
- Depois de registrar pela aba de /register.html, o usuario é cadastrado no banco de dados

![banco.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/463f6ec7-9441-499d-8998-0a4036f8336e/banco.png)

## Login.html

![login.PNG](https://prod-files-secure.s3.us-west-2.amazonaws.com/966f58b0-5278-4aa3-8556-cdd1d222728d/430ee396-e516-4dc7-a03c-5ea81e1212c5/login.png)

- **login.html**: Página de login que permite ao usuário inserir seu nome de usuário e senha.
- **Formulário de Login**: Envia uma requisição GET para `/login` com o nome de usuário e senha.
- **Script JavaScript**: Processa a resposta do servidor e armazena o token JWT no `localStorage`.
- Usando o Insomnia consigo fazer uma requisição GET diretamente pro meu Banco de Dados e ele recupera o registro adiconado do usuario e seu codigo JWT

## 

##
