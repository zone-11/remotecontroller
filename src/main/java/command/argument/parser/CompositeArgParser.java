package command.argument.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

class CompositeArgParser<T> implements ArgParser<T> {

    private final List<AbstractArgParser<? extends T>> parsers;
    private ListIterator<AbstractArgParser<? extends T>> parserIterator;

    public CompositeArgParser(List<AbstractArgParser<? extends T>> parsers) {
        this.parsers = parsers;
        this.parserIterator = parsers.listIterator();
    }

    @Override
    public Optional<List<T>> parse(List<String> args) {
        if (parserIterator.hasNext() && !args.isEmpty()) {
            var parser = parserIterator.next();
            for (int i = 1; i <= args.size(); i++) {
                var parsingArgs = parser.parse(args.subList(0, i));
                if (parsingArgs.isEmpty()) continue;

                var outArguments = new ArrayList<T>(parsingArgs.get());
                if (parserIterator.hasNext()) {
                    var recursiveParse = parse(args.subList(i, args.size()));
                    if (recursiveParse.isPresent()) {
                        outArguments.addAll(recursiveParse.get());
                    } else {
                        break;
                    }
                } else if (args.size() - i > 0) break;
                reset();
                return Optional.of(outArguments);
            }
        }
        reset();
        return Optional.empty();
    }

    private void reset() {
        parserIterator = parsers.listIterator();
    }


    public static void main(String[] args) {
        ArgParser<?> parser = new CompositeArgParser<>(
          List.of(new StringArgParser(), new IntegerArgParser())
        );
        parser.parse(List.of("\"Hello world\"", "26"))
          .ifPresent(list -> {
              var str = (String)list.get(0);
              int num = (int)list.get(1);
              System.out.println(str + num);
          });
    }
}

