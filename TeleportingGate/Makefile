src=src/io/github/liuchibing/teleportinggate/Gate.java  src/io/github/liuchibing/teleportinggate/TeleportingGate.java

class=out/io/github/liuchibing/teleportinggate/Gate.class out/io/github/liuchibing/teleportinggate/TeleportingGate.class

~/nukkit/plugins/TeleportingGate.jar: $(class)
	cp resources/config.yml out/config.yml
	cp plugin.yml out/plugin.yml;
	jar cvf ~/nukkit/plugins/TeleportingGate.jar -C out/ .

$(class): $(src)
	javac -d out/ $(src) -cp ~/nukkit/nukkit-1.0-SNAPSHOT.jar

.PHONY: clean
clean:
	-rm $(class) ~/nukkit/plugins/TeleportingGate.jar
