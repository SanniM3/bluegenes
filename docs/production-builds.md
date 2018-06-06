# Building BlueGenes for production 

The steps details in [getting-started](getting-started.md) are designed to get you up and running for developing BlueGenes. 
But now that you've completed your snazzy updates and you want to deploy the BlueGenes to production, how do you do it?
There are actually a few ways, depending in your needs. 

## Standalone BlueGenes

If you have a [heroku](https://www.heroku.com/) or [dokku](https://github.com/dokku/dokku) server, you can launch a version 
of InterMine that's mine-agnostic(ish). 

### Minified deployment using dokku
One of the easiest ways to deploy the prod minified version is to set up [Dokku](http://dokku.viewdocs.io/dokku/) on your intended server. Once dokku is configured on your remote host, all you need to do to deploy a minified build is add the server as a remote and push to it:

```bash
	git remote add my-awesome-server bluegenes@my-awesome-server.git
        git push my-awesome-server master
```


### Uberjar

It's also possible to compile BlueGenes to a jar that will automatically launch a server when executed. 

To compile and package BlueGenes into an executable jar, run the following command in the project folder:
```bash
$ lein uberjar
```
Then, to start the application, execute the jar and pass in one of the `config.edn` files from above:

```bash
$ java -jar -Dconfig="config/prod/config.edn" target/bluegenes.jar
```

(When executing the jar the `config.edn` file can be located anywhere, including your home directory for security.)