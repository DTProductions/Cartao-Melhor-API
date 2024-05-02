# Cartao Melhor API

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.pt-br.md)


API for fetching local bus pass data from the Cartão Melhor website, detailing its use, in the municipality of Cachoeiro de Itapemirim, Brazil. 

# Description

This API scrapes the website's extract page in search for travel records and their data for a certain user, given their bus pass number. The result is then returned via JSON containing all available data fields.

# Usage

Initially, a POST request has to be sent to the "/extract" endpoint alongside JSON in its body containing a key called "cardNum", which should store a pass card number.

> The card number needs to be in the format: xx.xx.xxxxxxxx-x

If the card number is empty or an exception occurs while scraping the data the JSON response will be returned with a "failure" status and no travel data.

Otherwise, the JSON response returns a "success" status and the fetched travel data.
