# Cartao Melhor API

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.pt-br.md)

API para coleta de dados do passe de ônibus municipal através do site do Cartão Melhor, detalhando seu uso, no município de Cachoeiro De Itapemirim, ES.

# Descrição

Esta API faz web scraping do site na página de extrato em busca dos registros de uso do cartão para um certo usuário, dado seu número de passe. O resultado então é retornado via JSON contendo todos os dados disponíveis na página.

# Dados

Os seguintes dados são disponibilizados pela API:

- day
- time
- sequence
- line
- vehicle
- direction
- status
- integration
- payment
- balance

# Instruções de Uso

Inicialmente, um request POST deve ser enviado para o endpoint "/extract" adjunto de JSON em seu body contendo uma chave chamada "cardNum", cujo valor é o número do passe a ser consultado.

> O número do cartão deve estar no formato: xx.xx.xxxxxxxx-x

Ademais, é possível selecionar dados específicos através da chave "fields" ao fazer o request em JSON, cujo valor é uma array de strings com o nome de cada dado selecionado.

> Caso a chave "fields" não seja definida, todos os dados serão retornados.

Caso o número do cartão seja vazio ou uma exceção ocorra durante a execução do scraping, uma response em JSON será retornada com status "failure", sem nenhum dado do extrato.

Caso contrário, a response em JSON retorna o status "success" e as informações coletadas.

## Exemplo de uso

Abaixo é possível ver uma forma na qual o payload em JSON poderia ser definido.

```
{
  "cardNum": "xx.xx.xxxxxxxx-x",
  "fields": ["day", "time", "line"]
}
```

E como a resposta da API poderia estar estruturada:

```
{
  "status": "success",
  "travels":  [
                {
                    "day": "xx/xx/xxxx",
                    "time": "xx:xx:xx",
                    "line": "xxx - xxxxxxxxx"
                },
                {
                    "day": "xx/xx/xxxx",
                    "time": "xx:xx:xx",
                    "line": "xxx - xxxxxxxxx"
                },
                {
                    "day": "xx/xx/xxxx",
                    "time": "xx:xx:xx",
                    "line": "xxx - xxxxxxxxx"
                }
              ]
}
```
