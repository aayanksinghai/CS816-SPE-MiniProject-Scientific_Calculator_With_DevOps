FROM openjdk:17.0.1-jdk-slim

# Install Xvfb (Virtual Display), VNC, NoVNC (Web VNC), and a basic window manager
RUN apt-get update && apt-get install -y \
    xvfb \
    x11vnc \
    novnc \
    websockify \
    openbox \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the compiled JAR
COPY target/scientific-calculator-1.0-SNAPSHOT.jar app.jar

# Create a startup script that handles the virtual screen and web server
RUN echo '#!/bin/bash\n\
export DISPLAY=:0\n\
# 1. Start a virtual screen (800x600 resolution)\n\
Xvfb :0 -screen 0 800x600x24 &\n\
sleep 2\n\
# 2. Start a window manager (gives the calculator a title bar and borders)\n\
openbox-session &\n\
# 3. Start the VNC server to capture the virtual screen\n\
x11vnc -display :0 -nopw -listen localhost -xkb -forever &\n\
# 4. Start your Java Calculator\n\
java -jar app.jar &\n\
# 5. Start NoVNC to serve the screen to a web browser on port 8080\n\
websockify --web=/usr/share/novnc/ 8080 localhost:5900\n\
' > /app/start.sh && chmod +x /app/start.sh

# Expose the web port
EXPOSE 8080

# Run the startup script
CMD ["/app/start.sh"]