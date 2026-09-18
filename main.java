// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}
// Main User Interaction with Portfolio.
// Extension of input/output interaction provided by main.
public void interact()
{
    String[] validOptions;
    String name;
    int price;

    validOptions = new String[] {
        "Display", "Buy", "Sell", "Wait", "Exit"
    };

    String option = "";

    while (!option.equals("Exit"))
    {
        updateMarket();

        validOptions = new String[] {
            "Display", "Buy", "Sell", "Wait", "Exit"
        };

        option = validInput(
            "What would you like to do?",
            validOptions
        );

        if (option.equals("Display"))
        {
            System.out.println(getBalance());
            System.out.println(isProfitable());

            for (Stock stock : this)
            {
                System.out.println(stock);
            }
        }
        else if (option.equals("Buy"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you spending?",
                    validOptions
                )
            );

            buyStock(name, price);
        }
        else if (option.equals("Sell"))
        {
            name = "";

            while (name.equals(""))
            {
                name = input("Please enter a stock name");
            }

            validOptions = new String[] {
                "10", "100", "500", "1000", "2500", "10000"
            };

            price = Integer.parseInt(
                validInput(
                    "How much $ are you selling?",
                    validOptions
                )
            );

            sellStock(name, price);
        }
        else if (option.equals("Wait"))
        {
            System.out.println(
                "You survey the market with the regality of a lion plotting " +
                "to sink its teeth into an antelope’s succulent thighs. " +
                "Perchance that dreamy feast awaits another warm night"
            );
        }
        else if (option.equals("Exit"))
        {
            System.out.println(
                "You run away from the bear with your tail between your legs :("
            );
        }
        else
        {
            System.out.println("Universe explodes");
        }
    }
}