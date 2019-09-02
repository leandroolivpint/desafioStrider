Estrutura de diretórios:

 - webservice (Diretorio onde está o projeto da API)
 - desafioStrider (Diretório onde está a interface web)
 - desafioStriderM (Diretório onde está o projeto mobile)

Link para o video de demostração 

https://www.youtube.com/watch?v=F9ju04C3EoU

Para rodar api 

É necessário criar o banco de dados no mysql manualmente
 - create database desafiobd

Pelo terminal, na pasta raiz do projeto da api, executar:
 - npm start  

Interface web

Pelo terminal, na pasta raiz do projeto web, executar:
 - ng serve

A interface web está na porta 4200
Acesse:
http://localhost:4200/

Mobile

O projeto mobile foi enviado em forma de projeto do Android Studio.
Para executar é necessário passar o endereço ip da api. Ou para utilizar apelido de localhost
é necessário:

Windows

1 - Adicionar o adb nas variáveis de ambiente,
2 - Executar o emulador android ou conectar um dispositivo mobile
3 - Executar: adb reverse tcp:3333 tcp:3333 (para mapear o localhost do dispositivo móvel no localhost da máquina onde está a API.)

Em seguida basta rodar o projeto do aplicativo no Android Studio.

Observações: 

 - Para atualizar a lista de tarefas pendentes no dispositivo móvel é necessário clicar no botão de atualizar na interface.
 - A lista de tarefas cadastradas na api é atualizada a cada 10 segundos na interface web

TECNOLOGIAS UTILIZADAS

Mobile:

A parte mobile da aplicação foi desenvolvida em Java, utilizando a IDE Android Studio, para a plataforma Android.


Web:

A interface web da aplicação foi desenvolvida em Angula 7, como uma SPA. Para o design foi utilizada o Angular Material.

API:

Para backend foi desenvolvido uma API Rest, utilizando a biblioteca Express da plataforma Nodejs. 



