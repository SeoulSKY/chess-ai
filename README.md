# Chess AI

https://user-images.githubusercontent.com/48105703/176050777-5f6dd86e-5986-4829-96b5-d16a8a9b18aa.mp4

## How to run
This application requires `Docker` and `Docker compose`. Please install them first.
* [Docker installation](https://www.docker.com/get-started)
* [Docker compose installation](https://docs.docker.com/compose/install/)

To run the app, use the following command:
```
sudo docker-compose up --build
```

When `web-server` prints `Accepting connections at http://localhost:8080` and `ai-server` prints `AI Server is up and running. Ready to receive requests`, it is ready to be used. Then, visit [here](http://localhost:8080) to play chess against AI.
