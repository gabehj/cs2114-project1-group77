# cs2114-project1-group77 — InfiniStocks

This is a stock simulator app that allows individuals to invest and watch
their assets fluctuate. Everything happens on the command line: you open a
portfolio with a fictional $10,000, buy and sell shares of six made-up
securities, and every action you take moves the market forward one day.
Everything lives in memory for the length of one run; nothing is written
to disk, so quitting starts you over next time.

## Running it

Run `Main` (right-click `Main.java` in Eclipse and choose Run As > Java
Application, or `java Main` from the project folder).

## Playing

The main menu lets you view, select, create, and delete portfolios. Menu
answers can be typed as the option's name in any capitalization (`buy`,
`BUY`, `Buy`) or as its number in the list. Anything else is rejected and
the question is asked again.

Inside a portfolio:

| Option  | What it does |
|---------|--------------|
| Display | Shows your cash, every holding, total value, and profit or loss |
| Market  | Lists every stock with its price and how much it moved today |
| Buy     | Pick a stock and a number of shares (capped at what you can afford) |
| Sell    | Pick one of your holdings and a number of shares (capped at what you own) |
| Deposit | Add cash to the portfolio (deposits are not counted as profit) |
| Wait    | Do nothing and let a day pass |
| Exit    | Return to the main menu |

## The market

| Stock      | Category | Volatility |
|------------|----------|------------|
| USA        | Bond     | 2          |
| NK, Grok   | Tech     | 15         |
| Solar City | Energy   | 25         |
| s&p5, QQQ  | ETF      | 12         |

Each day every price moves by a random percentage between minus half its
volatility and plus its volatility, so bonds crawl and energy stocks swing.

## Classes

| Class | Role |
|-------|------|
| `Stock` | A security: name, price, volatility, volume; `update()` moves the price |
| `Bond`, `Tech`, `Energy`, `ETF` | Stock subclasses that fix the volatility of their category |
| `Holding` | A stock plus the number of shares of it that a portfolio owns |
| `Market` | The six tradable stocks and the day counter; updates every price at once |
| `Portfolio` | Cash, holdings, `buy()`/`sell()` with overbuy/oversell checks, profit, and the portfolio menu |
| `Input` | One shared `Scanner` plus the try/catch-in-a-loop prompts that reject bad input |
| `InputEndedException` | Thrown when input runs out so the game can exit cleanly |
| `Money` | Rounds and formats dollar amounts |
| `Main` | The entry point and the main menu |

## Tests

Every class has a matching `*Test.java` written with `student.TestCase`
(the Web-CAT library used in CS 2114). The menu tests script the player's
input and check what is printed and what the portfolios look like
afterwards.
