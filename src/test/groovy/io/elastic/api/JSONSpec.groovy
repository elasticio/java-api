import io.elastic.api.JSON
import spock.lang.Specification
import spock.lang.Unroll

import javax.json.Json

@Unroll
class JSONSpec extends Specification {

    def "parse JSON object from #input results in #result"() {
        expect:
        JSON.parseObject(input) == result

        where:
        input << [null, "{}"]
        result << [null, Json.createObjectBuilder().build()]
    }

    def "parse JSON array from #input results in #result"() {
        expect:
        JSON.parseArray(input) == result

        where:
        input << [null, "[]"]
        result << [null, Json.createArrayBuilder().build()]
    }

    def "parsing a JSON array as JSON fails"() {
        when:
        JSON.parseObject("[]")

        then:
        def e = thrown(javax.json.JsonException)
        e.message == "Cannot read JSON object, found JSON array"
    }

    def "stringify"() {
        setup:
        def json = Json.createObjectBuilder()
                .add('hello', 'world')
                .build();
        expect:
        JSON.stringify(json) == '{"hello":"world"}'
    }
}