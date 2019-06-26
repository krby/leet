# DOES NOT WORK properly
# To execute the binary on macOS, use `java -jar Leet`

MAINCLASS  = Leet
JAVASRC    = $(wildcard *.java)
# JAVASRC    = $(shell find . -type f -name '*.java')
CLASSES    = $(shell find . -type f -name '*.class')
JARFILE    = $(MAINCLASS)
JARCLASSES = $(CLASSES)

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	rm Manifest
	chmod +x $(JARFILE)

$(CLASSES): $(JAVASRC)
	javac $(JAVASRC)

clean:
	rm -f $(CLASSES) $(JARFILE)
