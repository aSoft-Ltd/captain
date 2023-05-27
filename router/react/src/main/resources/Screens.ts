interface ScreenProps extends PropsWithChildren


const ScreenContext = createProvider()
function Screens(props: ScreenProps) {
    return (
        <ScreenContext.Provider>
            props.child()
        </ScreenContext.Provider>
    )
}


const val App = () => {
    return (
        <Screens>
            <Screen path="/home"><Test/></Screen>
            <Screen path="/about"><Test/></Screen>
        </Screens>
    )
}