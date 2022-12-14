import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class DistressSignalTest {

    private val distressSignal = DistressSignal()

    @Test
    fun loadData() {

        // act
        val packets = distressSignal.loadData(Path("src", "test", "resources", "Day13_TestData.txt"))

        // assert
        assertThat(packets).hasSize(8)
    }

    @Test
    fun decode_packet1_correct() {

        // act
        val order = distressSignal.decode(PairOfPacket("[1,1,3,1,1]", "[1,1,5,1,1]"))

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_packet2_correct() {

        // act
        val order = distressSignal.decode(PairOfPacket("[[1],[2,3,4]]", "[[1],4]"))

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_packet3_wrong() {

        // act
        val order = distressSignal.decode(PairOfPacket("[9]", "[[8,7,6]]"))

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_packet4_correct() {

        // act
        val order = distressSignal.decode(PairOfPacket("[[4,4],4,4]", "[[4,4],4,4,4]"))

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_packet5_wrong() {

        // act
        val order = distressSignal.decode(PairOfPacket("[7,7,7,7]", "[7,7,7]"))

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_packet6_correct() {

        // act
        val order = distressSignal.decode(PairOfPacket("[]", "[3]"))

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_packet7_wrong() {

        // act
        val order = distressSignal.decode(PairOfPacket("[[[]]]", "[[]]"))

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_packet8_wrong() {

        // act
        val order = distressSignal.decode(PairOfPacket("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]"))

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun getToken_startList() {

        // act
        val token = Packet("[").nextToken()

        // assert
        assertThat(token).isEqualTo(Token.StartListToken)
    }

    @Test
    fun getToken_endList() {

        // act
        val token = Packet("]").nextToken()

        // assert
        assertThat(token).isEqualTo(Token.EndListToken)
    }

    @Test
    fun getToken_IntegerToken_oneDigit() {

        // act
        val token = Packet("1").nextToken()

        // assert
        assertThat(token).isInstanceOf(Token.IntegerToken::class.java)
    }

    @Test
    fun getToken_IntegerToken_twoDigits() {

        // act
        val token = Packet("10").nextToken()

        // assert
        assertThat(token).isInstanceOf(Token.IntegerToken::class.java)
        val integerToken = token as Token.IntegerToken
        assertThat(integerToken.value).isEqualTo(10)
    }

    @Test
    fun hasMoreToken_true() {

        // act
        val hasMore = Packet("1").hasMoreToken()

        // assert
        assertThat(hasMore).isTrue
    }

    @Test
    fun hasMoreToken_false() {

        // act
        val hasMore = Packet("").hasMoreToken()

        // assert
        assertThat(hasMore).isFalse
    }

    @Test
    fun decodeAll() {
        // arrange
        val packets = distressSignal.loadData(Path("src", "test", "resources", "Day13_TestData.txt"))

        // act
        val result = distressSignal.decodeAll(packets)

        // assert
        assertThat(result).isEqualTo(13)
    }

    @Test
    fun decode_twoDigitValue_correct() {
        // arrange

        // act
        val order = distressSignal.decode(PairOfPacket("[8]", "10"))

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_twoDigitValue_wrong() {
        // arrange

        // act
        val order = distressSignal.decode(PairOfPacket("[10]", "8"))

        // assert
        assertThat(order).isEqualTo(Order.WRONG)

    }

    @Test
    fun decode_real_packet1_correct() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[5,[[3,0]],[0,[8,6,9],2,9],[5,6,[2,8,3],[0]],[6,2,[2,6,8],10]]]",
                "[[6,4,[2,[2,2],8,[3,7,0,6,2]]],[4,10]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_real_packet2_wrong() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[[6,[10,9,7]],9,[9,[9,0,8,3],[0],3],[8,1]],[2,10,9],[[0,6,[4]],2,[2,3,[6,7,0],[1,4,2]],[[3,7,2],[],0],9],[],[[]]]",
                "[[1,[1,10]]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_real_packet3_correct() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[4,[[9,8,1],2],10,6,[[],[],7]]]",
                "[[9,4,10],[0,[1,[10,4,6],[1],8],[[6,2,7,2,4],[9,3,5,6],[0,3,7],10],3],[[[5,4],2,10],2,9,4,[[6,7,2,3],[6]]],[],[]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_real_packet4_correct() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[0,1],[[7,[10,0,8,1],1,4]],[]]",
                "[[1,3,2,3],[1,[[],7,[6,5,8,7,9],[0,2]],[8],5,[]],[[7,8,[],[8,9,4]],[2]]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_real_packet5_correct() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[7,[[6]],[4,9]]]",
                "[[10],[],[[10,[10,10,5,2,1],[10],5],[4,4],[[2,1],[9]],6],[6,8]]"

            )
        )

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_real_packet7_wrong() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[6,5,5,1],[8],[]]",
                "[[1,[]],[10],[10,[8,[4]],6,1],[5]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_real_packet8_correct() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[[[1,1,2,4],[7,8,8,7],5,[9,1,7,0,8],[2,10,10,4]],10,[7,[6,10],[6,10,6,9],[0,8,10],7]],[8,[[],[],[1,3,0,5,10],[7,0,7]],1],[]]",
                "[[6,3,[],5],[]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_real_packet10_correct() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[[[],[5,0,8,5,4],[2],0,[]],7,6,10,[7,1,10,9]],[]]",
                "[[[8,[1,2,3]]],[8],[6,9,[3,1,1,[10,10,3,10,0]],3,0],[[[8,7]]]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_real_packet11_wrong() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[0,[[0],5,[6,3,3],[10,3,10,9]],4]]",
                "[[],[5],[8],[[[10,8,10,5,3],[3,7,10,9,8],0],[[],[]]]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_real_packet12_wrong() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[],[10,[7,3,[6],7],[9,[3],[8],7,[1]],10],[6,9,[9,3],[[4,8,1],[10,1,4,7],7],10],[[],[3]]]",
                "[[[6],5,6,10,6],[[]],[1,[6,10],8,3],[1]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_real_packet13_wrong() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[1,3],[10,[2],8,1,[6,6,[5],5,[2]]],[[4]],[],[]]",
                "[[[],[1,0,[8],8,[0,3,6]],1],[[[6,1],8,3,1],1,[6,[6,9,3,8,7]],10,[5,[4]]]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_real_packet14_wrong() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[9,[6,2,[],[3,0,2,7]],0],[[[5,4,0],0,[2],[7,3,2,5,1]],9,3]]",
                "[[2,[7,[5,2,4],8,5,[3,0,4,5,4]],0,8]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }

    @Test
    fun decode_real_packet16_correct() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[],[6],[[[3,6,3,9]],[[2,0],7,1,[10,10,4,4,10]]]]",
                "[[4,[[6,8,4,9,2],[],5,[10,1]],1,6],[[[4,2,5],4]],[[3,6]],[7,8,[[5,8,9,10],1,[6,8],1,7]]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.CORRECT)
    }

    @Test
    fun decode_real_packet17_wrong() {

        // act
        val order = distressSignal.decode(
            PairOfPacket(
                "[[[[],10,7],[],[1,[]],8,[9,0]],[0,[[6],3,1,[0,7,5]],[[7,6,3,9,3],4,4],[],0],[],[9,2,[9,1,[0,9,4,6],[9,2],10],9]]",
                "[[],[2,5,6,5,8]]"
            )
        )

        // assert
        assertThat(order).isEqualTo(Order.WRONG)
    }


}