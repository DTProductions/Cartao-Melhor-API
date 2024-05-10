# Cartao Melhor API

[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.pt-br.md)


API for fetching local bus pass data from the Cartão Melhor website, detailing its use, in the municipality of Cachoeiro de Itapemirim, Brazil. 

# Description

This API scrapes the website's extract page in search of travel records and their data for a certain user, given their bus pass number. The result is then returned via JSON containing all available data fields.

# Data Fields

The following fields are available for retrieval:

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

# Usage

Initially, a POST request has to be sent to the "/extract" endpoint alongside JSON in its body containing a key called "cardNum", which should store a pass card number as a string.

> The card number needs to be in the format: xx.xx.xxxxxxxx-x

Furthermore, if you'd like to select specific data fields, send a JSON key "fields" alongside an array of strings specifying which fields you'd like to retrieve.

> If the "fields" key is not set, all fields will be retrieved.

If the card number is empty or an exception occurs while scraping the data, the JSON response will be returned with a "failure" status and no travel data.

Otherwise, the JSON response returns a "success" status and the fetched travel data.

## Example

Below is an example of how the JSON payload may be structured.

```
{
  "cardNum": "xx.xx.xxxxxxxx-x",
  "fields": ["day", "time", "line"]
}
```

And what the API response may look like:

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
